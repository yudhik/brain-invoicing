package com.brainmaster.mobitronik.bean.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.CompositeProductStore;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.model.ProductStorePriceAccumulation;
import com.brainmaster.mobitronik.model.ProductStoreTransaction;
import com.brainmaster.mobitronik.model.Store;

@Service
@Transactional(readOnly = true)
public class ProductStoreService {

	private static final Logger log = LoggerFactory.getLogger(ProductStoreService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addProductStore(ProductStore productStore) {
		log.debug("adding product " + productStore.getProduct().getUuid() + " to store : " + productStore.getStore().getUuid());
		em.persist(productStore);
	}

	@Transactional(readOnly = false)
	public void addProductStoreTransaction(ProductStoreTransaction productStoreQuantityTransaction) {
		em.persist(productStoreQuantityTransaction);
	}

	@Transactional(readOnly = false)
	public void removeProductStore(ProductStore productStoreQuantity) {
		em.remove(productStoreQuantity);
	}

	public ProductStore findStoreQuantity(
			CompositeProductStore compositeProductStore) {
		return em.find(ProductStore.class, compositeProductStore);
	}

	public ProductStoreTransaction findLatestProductStoreTransaction(ProductStore productStore) {
		ProductStoreTransaction productStoreTransaction = null;
		try {
			productStoreTransaction = em.createNamedQuery("latest-product-store-quantity-transaction",
					ProductStoreTransaction.class).setParameter("productStore", productStore).getSingleResult();
		} catch (NoResultException e) {
			log.warn("no latest product store quantity transaction for product store with id : " + productStore.getId().toString());
		}
		return productStoreTransaction;
	}

	public List<ProductStore> findAllStoreQuantity() {
		return em.createQuery("from ProductStore", ProductStore.class).getResultList();
	}

	@Transactional(readOnly = false)
	public void updateQuantity(UUID storeId, UUID productId, Integer quantity) {
		Product product = em.find(Product.class, productId);
		Store store = em.find(Store.class, storeId);
		ProductStore productStore = em.find(ProductStore.class,
				new CompositeProductStore(product, store));
		productStore.setQuantity(quantity);
		em.persist(productStore);
		em.flush();
	}

	public ProductStorePriceAccumulation findLatestProductStorePriceAccumulation(
			ProductStore productStore) {
		ProductStorePriceAccumulation productStorePriceAccumulation = null;
		try {
			productStorePriceAccumulation = (ProductStorePriceAccumulation) em.createNamedQuery("latest-product-store-average-price")
					.setParameter("productStore", productStore).getSingleResult();
		} catch (NoResultException e) {
			log.warn("no latest product store price accumulation for product store with id : " + productStore.getId().toString());
		}
		return productStorePriceAccumulation;
	}
}
