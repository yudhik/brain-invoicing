package com.brainmaster.mobitronik.bean.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.brainmaster.mobitronik.dto.UserData;
import com.brainmaster.mobitronik.model.User;
import com.brainmaster.mobitronik.model.UserRole;
import com.brainmaster.mobitronik.model.UserStore;

@Service
@Transactional(readOnly = true)
public class UserService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addUser(User user) {
		if (log.isDebugEnabled())
			log.debug("add new user with email " + user.getEmailAddress());
		if (user.getRoles() != null && user.getRoles().size() > 0) {
			for (UserRole role : user.getRoles())
				role.setUser(user);
		}
		if (user.getStores() != null && user.getStores().size() > 0) {
			for (UserStore store : user.getStores())
				store.setUser(user);
		}
		user.setPassword(ArrayUtils.toObject(DigestUtils.appendMd5DigestAsHex(
				ArrayUtils.toPrimitive(user.getPassword()), new StringBuilder()).toString().getBytes()));
		em.persist(user);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void removeUser(UserData user) {
		log.debug("remove user with email " + user.getEmailAddress());
		em.remove(em.merge(em.find(User.class, user.getEmailAddress())));
		em.flush();
	}
	
	@Transactional(readOnly = false)
	public void updateUser(UserData user) {
		log.info("updating user : {}", user.getEmailAddress());
		User entity = em.find(User.class, user.getEmailAddress());
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		entity.setPhoneNumber(user.getPhoneNumber());
		entity.setPassword(ArrayUtils.toObject(user.getPassword().getBytes()));
		em.merge(entity);
		em.flush();
	}

	public User find(String email) {
		log.info("find user with email " + email);
		User user = em.find(User.class, email);
		if (user != null) {
			Hibernate.initialize(user.getRoles());
			Hibernate.initialize(user.getStores());
		}
		return user;
	}

	public List<User> findAll() {
		if (log.isDebugEnabled())
			log.debug("find all user");
		return em.createQuery("from User", User.class).getResultList();
	}

}
