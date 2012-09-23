package com.brainmaster.mobitronik.bean;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.Store;

@Component("storeDao")
public class StoreDao extends GenericDao<Store> {

	private static final Logger log = LoggerFactory.getLogger(StoreDao.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	@Transactional
	public void add(Store t) {
		try {
			save(t);
		} catch (Exception e) {
			log.error("can not add store", e);
		}
	}

	@Override
	@Transactional
	public void delete(Store t) {
		try {
			remove(t);
		} catch (Exception e) {
			log.error("can not add store", e);
		}
	}

	@Override
	public List<Store> getAll(Store t) {
		return findAll(t);
	}

	public Store get(UUID uuid) {
		return em.find(Store.class, uuid);
	}

}
