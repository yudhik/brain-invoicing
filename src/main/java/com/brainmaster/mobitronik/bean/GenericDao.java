package com.brainmaster.mobitronik.bean;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class GenericDao<T> {

	protected void save(T t) throws Exception {
		getEntityManager().persist(t);
		getEntityManager().flush();
	}

	protected void remove(T t) throws Exception {
		getEntityManager().remove(t);
		getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findAll(T t) {
		return getEntityManager().createQuery("from " + t.toString()).getResultList();
	}

	protected abstract EntityManager getEntityManager();

	public abstract void add(T t);

	public abstract void delete(T t);

	public abstract List<T> getAll(T t);
}
