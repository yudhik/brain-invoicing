package com.brainmaster.mobitronik.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class LoginPage {

	private static Logger log = LoggerFactory.getLogger(LoginPage.class);

	private String username;
	private String password;

	public String doLogin() throws IOException, ServletException {
		log.debug("try to authenticate user : " + getUsername() + ", password : " + getPassword());
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	@PostConstruct
	@SuppressWarnings("unused")
	private void handleErrorMessage() {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nama pengguna atau kata sandi tidak dapat ditemukan", null));
		}
	}

	public boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	public List<String> getAuthorities() {
		List<String> roles = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> grantedAuthorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		for (SimpleGrantedAuthority grantedAuthority : grantedAuthorities) {
			String authority = grantedAuthority.getAuthority();
			roles.add(authority.substring(authority.indexOf("_")));
		}
		return roles;
	}

	public String getUsername() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (isAuthenticated() && !"anonymousUser".equals(name)) {
			return name;
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
