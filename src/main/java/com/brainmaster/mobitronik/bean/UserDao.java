package com.brainmaster.mobitronik.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.User;

@Component
public class UserDao extends GenericDao<User> {

	private static final Logger log = LoggerFactory.getLogger(UserDao.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	@Transactional
	public void add(User t) {
		try {
			save(t);
		} catch (Exception e) {
			log.error("could not add user", e);
		}
	}

	@Override
	@Transactional
	public void delete(User t) {
		try {
			remove(t);
		} catch (Exception e) {
			log.error("could not delete user", e);
		}
	}

	@Override
	public List<User> getAll(User t) {
		return findAll(t);
	}

	public User get(String email) {
		return em.find(User.class, email);
	}

}
