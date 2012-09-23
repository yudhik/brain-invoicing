package com.brainmaster.mobitronik.bean.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.Role;

@Service
@Transactional(readOnly = true)
public class RoleService {

	private static final Logger log = LoggerFactory.getLogger(RoleService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addRole(Role role) {
		if (log.isDebugEnabled())
			log.debug("add new role with name " + role.getRole());
		em.persist(role);
	}

	@Transactional(readOnly = false)
	public void removeRole(Role role) {
		if (log.isDebugEnabled())
			log.debug("remove role with name " + role.getRole());
		em.remove(role);
	}

	public Role find(String role) {
		if (log.isDebugEnabled())
			log.debug("find role with name " + role);
		Role roleEntity = em.getReference(Role.class, role);
		if (roleEntity != null) {
			Hibernate.initialize(roleEntity.getUsers());
		}
		return roleEntity;
	}

	public List<Role> findAll() {
		if (log.isDebugEnabled())
			log.debug("find all role");
		return em.createQuery("from Role", Role.class).getResultList();
	}
}
