package com.brainmaster.mobitronik.bean.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.DocumentType;

@Service
@Transactional(readOnly = true)
public class SequenceNumberGeneratorService {
	
	private static final Logger log = LoggerFactory.getLogger(SequenceNumberGeneratorService.class);

	@PersistenceContext
	private EntityManager em;
	
	public int getNextSequence(DocumentType documentType) {
		//next need to create the sequence table for improve the performance
		try {
			return em.createNamedQuery("count-document-type-from-date")
					.setParameter("documentType", documentType)
					.setParameter("invoiceDate", new Date()).getResultList().size() + 1;
		} catch (NoResultException e) {
			log.error("can not find count-document-type-from-date, assign 0 as the value", e);
			return 1;
		}
	}
}
