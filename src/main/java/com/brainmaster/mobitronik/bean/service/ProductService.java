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

import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Service
@Transactional(readOnly = true)
public class ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addProduct(Product product) {
		if (log.isDebugEnabled())
			log.debug("add new product with uuid " + product.getProductCode());
		em.persist(product);
	}

	@Transactional(readOnly = false)
	public void removeProduct(Product product) {
		if (log.isDebugEnabled())
			log.debug("remove product with uuid " + product.getProductCode());
		em.remove(product);
	}

	public Product find(UUID uuid) {
		if (log.isDebugEnabled())
			log.debug("find product with uuid " + UUIDHelper.uuidToString(uuid));
		Product product = em.find(Product.class, uuid);
		if (product != null) {
			Hibernate.initialize(product.getBrand());
			Hibernate.initialize(product.getCategory());
		}
		return product;
	}

	public List<Product> findAll() {
		return em.createQuery("from Product", Product.class).getResultList();
	}

	public List<Product> findByProductCode(String productCode) {
		if (log.isDebugEnabled())
			log.debug("find product code like " + productCode);
		return em.createNamedQuery("product-by-code", Product.class)
				.setParameter("productName", "%" + productCode + "%").getResultList();
	}
}
