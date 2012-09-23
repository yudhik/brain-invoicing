package com.brainmaster.mobitronik.bean.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.Brand;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Service
@Transactional(readOnly = true)
public class BrandService {

	private static final Logger log = LoggerFactory.getLogger(BrandService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addBrand(Brand brand) {
		if (log.isDebugEnabled())
			log.debug("add new brand with uuid " + brand.getUuid());
		em.persist(brand);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void removeBrand(Brand brand) {
		brand = em.merge(brand);
		if (log.isDebugEnabled())
			log.debug("remove brand with uuid " + brand.getUuid());
		em.remove(brand);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void updateBrand(Brand brand) {
		em.persist(em.merge(brand));
		em.flush();
	}

	public Brand find(UUID uuid) {
		if (log.isDebugEnabled())
			log.debug("find brand with uuid " + UUIDHelper.uuidToString(uuid));
		Brand brand = em.find(Brand.class, uuid);
		if (brand != null) {
			Hibernate.initialize(brand.getProducts());
		}
		return brand;
	}

	public List<Brand> findAll() {
		if (log.isDebugEnabled())
			log.debug("find all brand");
		return em.createQuery("from Brand", Brand.class).getResultList();
	}
}
