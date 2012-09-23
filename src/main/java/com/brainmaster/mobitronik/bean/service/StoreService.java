package com.brainmaster.mobitronik.bean.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.CompositeProductStore;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Service
@Transactional(readOnly = true)
public class StoreService {

	private static final Logger log = LoggerFactory.getLogger(StoreService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addStore(Store store) {
		if (log.isDebugEnabled())
			log.debug("add new store with uuid " + UUIDHelper.uuidToString(store.getUuid()));
		em.persist(store);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void addProduct(UUID storeId, UUID productId) {
		Product product = em.find(Product.class, productId);
		Store store = em.find(Store.class, storeId);
		CompositeProductStore id = new CompositeProductStore(product, store);
		ProductStore ps = new ProductStore(id, null);
		em.persist(ps);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void updateQuantityOfProductAtStore(UUID storeId, UUID productId, Integer quantity) {
		Product product = em.find(Product.class, productId);
		Store store = em.find(Store.class, storeId);
		CompositeProductStore id = new CompositeProductStore(product, store);
		ProductStore ps = em.find(ProductStore.class, id);
		ps.setQuantity(quantity);
		em.persist(ps);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void removeStore(Store store) {
		if (log.isDebugEnabled())
			log.debug("delete store with uuid " + UUIDHelper.uuidToString(store.getUuid()));
		em.remove(store);
		em.flush();
	}

	public Store find(UUID uuid) {
		if (log.isDebugEnabled())
			log.debug("retrieve store with uuid " + UUIDHelper.uuidToString(uuid));
		Store store = em.getReference(Store.class, uuid);
		if (store != null) {
			try {
				Hibernate.initialize(store.getChildStores());
				Hibernate.initialize(store.getUsers());
			} catch (HibernateException e) {
				log.error("can not fetch the childs of store");
			}
		}
		return store;
	}

	public List<ProductStore> findProductForStore(Store store) {
		Store storeIn = em.find(Store.class, store.getUuid());
		Hibernate.initialize(storeIn.getProducts());
		List<ProductStore> resultStores = new ArrayList<ProductStore>();
		for (ProductStore productStore : storeIn.getProducts()) {
			resultStores.add(new ProductStore(
					new CompositeProductStore(productStore.getProduct(), productStore.getStore()), productStore.getQuantity()));
		}
		return resultStores;
	}

	public List<Store> findAll() {
		if (log.isDebugEnabled())
			log.debug("retrieve all store ");
		return em.createQuery("from Store", Store.class).getResultList();
	}
}
