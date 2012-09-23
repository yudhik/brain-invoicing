package com.brainmaster.mobitronik.primefaces.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.brainmaster.mobitronik.action.LoginBean;

@Component
public class LoginFilter extends GenericFilterBean {

	private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if (httpRequest.getSession(true).getAttribute("loginBean") == null) {
				httpRequest.getSession().setAttribute("loginBean", new LoginBean());
			} else {
				log.info("loginBean is on the header");
				LoginBean loginBean = (LoginBean) httpRequest.getSession().getAttribute("loginBean");
				if (!loginBean.isAuthenticate()) {
					log.info("user is not authenticate, forwarding page");
					if (!httpRequest.getRequestURI().contains("login.jsf")) {
						httpResponse.sendRedirect("login.jsf");
					}
				}
				chain.doFilter(httpRequest, httpResponse);
			}
		}
	}

}
