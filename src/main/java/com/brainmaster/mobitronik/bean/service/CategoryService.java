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

import com.brainmaster.mobitronik.model.Category;

@Service
@Transactional(readOnly = true)
public class CategoryService {

	private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public void addCategory(Category category) {
		if (log.isDebugEnabled())
			log.debug("add new category with uuid " + category.getUuid());
		em.persist(category);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void addParentCategory(UUID childUuid, UUID parentUuid) {
		Category parent = em.find(Category.class, parentUuid);
		Category child = em.find(Category.class, childUuid);
		parent.getChildCategories().add(child);
		child.setParentCategory(parent);
		em.persist(parent);
		em.flush();
	}

	@Transactional(readOnly = false)
	public void removeCategory(Category category) {
		if (log.isDebugEnabled())
			log.debug("remove category with uuid " + category.getUuid());
		em.remove(em.find(Category.class, category.getUuid()));
		em.flush();
	}

	public Category find(UUID uuid) {
		if (log.isDebugEnabled())
			log.debug("find category with uuid " + uuid);
		Category category = em.find(Category.class, uuid);
		if (category != null) {
			Hibernate.initialize(category.getProducts());
			Hibernate.initialize(category.getChildCategories());
		}
		return category;
	}

	public List<Category> findAll() {
		if (log.isDebugEnabled())
			log.debug("find all category");
		return em.createQuery("from Category", Category.class).getResultList();
	}

	@Transactional(readOnly=false)
	public void update(Category category) {
	    Category categoryEntity = em.find(Category.class, category.getUuid());
	    categoryEntity.setCategoryName(category.getCategoryName());
	    if(category.getParentCategory() != null) {
		Category parentEntity = em.find(Category.class, category.getParentCategory().getUuid());
		categoryEntity.setParentCategory(parentEntity);
	    }
	    em.persist(categoryEntity);
	    em.flush();
	}
}
