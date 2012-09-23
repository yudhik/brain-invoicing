package com.brainmaster.mobitronik.bean.service;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.RetailDocument;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Service
@Transactional(readOnly = true)
public class RetailDocumentService {

	@PersistenceContext
	private EntityManager em;

	public RetailDocument find(UUID uuid, boolean includeContent) {
		RetailDocument document = em.find(RetailDocument.class, uuid);
		if (includeContent)
			Hibernate.initialize(document.getDocumentContent());
		return document;
	}

	public String getDocumentId(String invoiceReference) {
		return UUIDHelper.uuidToString(((RetailDocument) em.createNamedQuery("documentid-with-invoice-reference")
				.setParameter("reference", invoiceReference).getSingleResult()).getUuid());
	}

}
