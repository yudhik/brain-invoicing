package com.brainmaster.mobitronik.bean.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.PackagingValue;

@Service
@Transactional(readOnly = true)
public class PackagingValueService {

	private static Logger log = LoggerFactory.getLogger(PackagingValueService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addPackagingValue(PackagingValue packagingValue) {
		log.debug("adding packaging value : " + packagingValue.getPackageId() + " with quantity : " + packagingValue.getContentQuantity());
		em.persist(packagingValue);
	}

	@Transactional(readOnly = false)
	public void removePackagingValue(PackagingValue packagingValue) {
		em.remove(packagingValue);
	}

	public PackagingValue find(String packagingId) {
		return em.find(PackagingValue.class, packagingId.toUpperCase());
	}

	public List<PackagingValue> findAll() {
		log.debug("get all packaging");
		return em.createQuery("from PackagingValue", PackagingValue.class).getResultList();
	}
}
