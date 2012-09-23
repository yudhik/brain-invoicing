package com.brainmaster.mobitronik.bean.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brainmaster.mobitronik.model.CompositeProductStore;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.InvoiceTransaction;
import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;
import com.brainmaster.mobitronik.model.Product;
import com.brainmaster.mobitronik.model.ProductStore;
import com.brainmaster.mobitronik.model.ProductStorePriceAccumulation;
import com.brainmaster.mobitronik.model.ProductStoreTransaction;
import com.brainmaster.mobitronik.model.RetailDocument;
import com.brainmaster.mobitronik.model.RetailDocumentContent;
import com.brainmaster.mobitronik.model.Store;
import com.brainmaster.util.formatter.XMLDateFormatter;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Service
@Transactional(readOnly = true)
public class InvoiceTransactionService {

	private static Logger log = LoggerFactory.getLogger(InvoiceTransactionService.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false)
	public UUID addInvoiceTransaction(InvoiceTransaction invoice) throws Exception {
		if(invoice == null) return null;
		Store seller = em.find(Store.class, invoice.getSeller().getUuid());
		Store buyer = em.find(Store.class, invoice.getBuyer().getUuid());
		for (InvoiceTransactionDetail detail : invoice.getTransactions()) {
			detail.setInvoiceTransaction(invoice);
		}
		RetailDocument document = generateDocument(invoice, buyer, seller);
		em.persist(invoice);
		em.persist(document);
		em.persist(document.getDocumentContent());
		for (InvoiceTransactionDetail detail : invoice.getTransactions()) {
			long outstandingTransactionQuantity = 0;
			long quantity = 0;
			Product product = em.find(Product.class, detail.getProduct());
			CompositeProductStore psId = null;
			if (DocumentType.INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				psId = new CompositeProductStore(product, seller);
			} else if (DocumentType.PURCHASE_INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				psId = new CompositeProductStore(product, buyer);
			}

			ProductStore productStore = em.find(ProductStore.class, psId);
			ProductStoreTransaction outstandingTransaction = null;

			try {
				outstandingTransaction = em.createNamedQuery("latest-product-store-quantity-transaction", ProductStoreTransaction.class)
						.setParameter("productStore", productStore).getSingleResult();
			} catch (NoResultException e) {
				log.debug("no previous transaction found for : {}", productStore);
			}
			if (outstandingTransaction != null)
				outstandingTransactionQuantity = outstandingTransaction.getOutstandingQuantity();

			if (DocumentType.INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				quantity = detail.getSerialNumber().contains(",") ? detail.getSerialNumber().split(",").length : 1;
				outstandingTransactionQuantity = outstandingTransactionQuantity + quantity;
			} else if (DocumentType.PURCHASE_INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				quantity = detail.getQuantity().intValue();
				ProductStorePriceAccumulation previousPriceAccumulation = null;
				try {
					previousPriceAccumulation = (ProductStorePriceAccumulation) em.createNamedQuery("latest-product-store-average-price")
							.setParameter("productStore", productStore).getSingleResult();
				} catch (NoResultException e) {
					log.debug("no previous transaction found for : {}", productStore);
				}
				BigDecimal previousPrice = BigDecimal.ZERO;

				if (previousPriceAccumulation == null)
					previousPrice = detail.getPrice();
				else
					previousPrice = previousPriceAccumulation.getAveragePrice();

				BigDecimal averagePrice = previousPrice.multiply(
						new BigDecimal(productStore.getQuantity().longValue() - outstandingTransactionQuantity))
						.add(detail.getPrice().multiply(new BigDecimal(detail.getQuantity())))
						.divide(new BigDecimal(productStore.getQuantity().longValue() - outstandingTransactionQuantity)
								.add(new BigDecimal(detail.getQuantity())), RoundingMode.HALF_EVEN);
				ProductStorePriceAccumulation priceAccumulation = new ProductStorePriceAccumulation(UUID.randomUUID(), productStore, document
						.getUuid(),
						averagePrice, detail.getPrice(), new Date());
				priceAccumulation.setProductStore(productStore);
				productStore.getAccumulations().add(priceAccumulation);
				outstandingTransactionQuantity = outstandingTransactionQuantity - quantity;
			}

			ProductStoreTransaction transaction = new ProductStoreTransaction(UUID.randomUUID(),
					new Date(), outstandingTransactionQuantity, quantity, document.getUuid());
			transaction.setProductStore(productStore);
			productStore.getTransactions().add(transaction);

			em.persist(productStore);
		}
		em.flush();
		log.debug("returning uuid {}",UUIDHelper.uuidToString(document.getUuid()));
		return document.getUuid();
	}

	private RetailDocument generateDocument(InvoiceTransaction invoice, Store buyer, Store seller) {
		if(invoice == null || buyer == null || seller == null) return null;
		UUID documentId = UUID.randomUUID();
		RetailDocumentContent documentContent = new RetailDocumentContent(documentId, ArrayUtils
				.toObject(buildInvoiceDocument(invoice, buyer, seller).getBytes()));
		RetailDocument document = new RetailDocument(documentId, seller.getUuid(), buyer.getUuid(), invoice.getInvoiceNumber(),
				invoice.getDocumentType(), new Date(), documentContent);
		documentContent.setRetailDocument(document);
		return document;
	}

	private String buildInvoiceDocument(InvoiceTransaction invoice, Store buyer, Store seller) {
		if(invoice == null || buyer == null || seller == null) return null;
		Element invoiceDocument = new DefaultElement("invoiceDocument");
		Document doc = DocumentHelper.createDocument(invoiceDocument);
		Element root = doc.getRootElement();
		root.addAttribute("createdDate", XMLDateFormatter.getXMLFormat(new Date()));

		Element buyerType = new DefaultElement("buyer");
		buyerType.addAttribute("id", UUIDHelper.uuidToString(invoice.getBuyer().getUuid()));
		buyerType.add(createNameElement(buyer.getStoreName()));
		buyerType.add(createStreetAddressOne(new String(ArrayUtils.toPrimitive(buyer.getAddress()))));
		buyerType.add(createPhoneNumber(buyer.getPhoneNumber()));

		Element sellerType = new DefaultElement("seller");
		sellerType.addAttribute("id", UUIDHelper.uuidToString(invoice.getSeller().getUuid()));
		sellerType.add(createNameElement(seller.getStoreName()));
		sellerType.add(createStreetAddressOne(new String(ArrayUtils.toPrimitive(seller.getAddress()))));
		sellerType.add(createPhoneNumber(seller.getPhoneNumber()));

		Element invoiceSummary = new DefaultElement("invoiceSummary");
		invoiceSummary.addElement("invoiceCurrencyISO").addText("IDR");
		invoiceSummary.addElement("invoiceNumber").addText(invoice.getInvoiceNumber());
		invoiceSummary.addElement("invoiceTotal").addText(invoice.getGrandTotal().toPlainString());
		invoiceSummary.addElement("remark");

		root.add(buyerType);
		root.add(sellerType);
		root.add(invoiceSummary);

		for (InvoiceTransactionDetail detail : invoice.getTransactions()) {
			log.debug("detail transaction : " + detail.toString());
			Element tradeItem = new DefaultElement("tradeItem");
			int quantity = 0;
			if (DocumentType.INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				quantity = detail.getSerialNumber().contains(",") ? detail.getSerialNumber().split(",").length : 1;
			} else if (DocumentType.PURCHASE_INVOICE.toString().equalsIgnoreCase(invoice.getDocumentType().toString())) {
				quantity = detail.getQuantity().intValue();
			}

			tradeItem.addAttribute("number", "" + invoice.getTransactions().indexOf(detail));
			tradeItem.addElement("gtin").addText(detail.getGtin() == null ? "00000000000" : detail.getGtin());

			Element additionalTradeInformation = tradeItem.addElement("additionalTradeInformation");
			Product product = em.find(Product.class, detail.getProduct());
			additionalTradeInformation.add(createTradeItemAdditionalInformation("serial_number", detail.getSerialNumber()));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("product_name", product.getProductName()));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("price", detail.getPrice().toPlainString()));
			additionalTradeInformation.add(createTradeItemAdditionalInformation("quantity", "" + quantity));

			tradeItem.addElement("total").addText(detail.getPrice().multiply(new BigDecimal(quantity)).toPlainString());

			root.add(tradeItem);
		}
		return root.asXML();
	}

	public List<InvoiceTransaction> getInvoicesFromSellerStore(Store seller) {
		return getInvoicesFromSellerStore(seller, new Date(), true);
	}

	public List<InvoiceTransaction> getInvoicesFromSellerStore(Store seller, boolean includeDetail) {
		return getInvoicesFromSellerStore(seller, new Date(), includeDetail);
	}

	public List<InvoiceTransaction> getInvoicesFromSellerStoreForDate(Store seller, Date date) {
		return getInvoicesFromSellerStore(seller, date, true);
	}

	public List<InvoiceTransaction> getInvoicesFromSellerStoreForDate(Store seller, Date date, boolean includeDetail) {
		return getInvoicesFromSellerStore(seller, date, includeDetail);
	}

	public List<InvoiceTransaction> getInvoicesFromBuyerStore(Store buyer) {
		return getInvoicesFromBuyerStore(buyer, new Date(), true);
	}

	public List<InvoiceTransaction> getInvoicesFromBuyerStore(Store buyer, boolean includeDetail) {
		return getInvoicesFromBuyerStore(buyer, new Date(), includeDetail);
	}

	public List<InvoiceTransaction> getInvoicesFromBuyerStoreForDate(Store buyer, Date date) {
		return getInvoicesFromBuyerStore(buyer, date, true);
	}

	public List<InvoiceTransaction> getInvoicesFromBuyerStoreForDate(Store seller, Date date, boolean includeDetail) {
		return getInvoicesFromBuyerStore(seller, date, includeDetail);
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceTransaction> getInvoicesFromBuyerStore(Store buyer, Date date, boolean includeDetail) {
		if(buyer == null) return new ArrayList<InvoiceTransaction>();
		List<InvoiceTransaction> invoiceTransactions = new ArrayList<InvoiceTransaction>();
		if (date == null)
			invoiceTransactions = em.createNamedQuery("invoice-with-buyer-store")
					.setParameter("buyer", buyer).getResultList();
		else
			invoiceTransactions = em.createNamedQuery("invoice-with-buyer-store-and-date")
					.setParameter("buyer", buyer).setParameter("date", date).getResultList();
		if (includeDetail)
			for (InvoiceTransaction transaction : invoiceTransactions)
				Hibernate.initialize(transaction.getTransactions());
		return invoiceTransactions;
	}

	public List<InvoiceTransaction> getInvoiceWithReference(String reference, boolean includeDetails) {
		try {
			@SuppressWarnings("unchecked")
			List<InvoiceTransaction> invoiceTransactions = em.createNamedQuery("invoice-with-reference")
					.setParameter("reference", String.valueOf("%").concat(reference).concat("%")).getResultList();
			if (includeDetails) {
				for (InvoiceTransaction transaction : invoiceTransactions)
					Hibernate.initialize(transaction.getTransactions());
			}
			return invoiceTransactions;
		} catch (IllegalArgumentException e) {
			log.error("something is not good when query with invoice-reference",e);
		}
		return new ArrayList<InvoiceTransaction>();
	}

	public InvoiceTransaction findInvoice(UUID uuid) {
		InvoiceTransaction invoiceTransaction = em.find(InvoiceTransaction.class, uuid);
		Hibernate.initialize(invoiceTransaction.getTransactions());
		return invoiceTransaction;
	}

	@SuppressWarnings("unchecked")
	private List<InvoiceTransaction> getInvoicesFromSellerStore(Store seller, Date date, boolean includeDetails) {
		if(seller == null) return new ArrayList<InvoiceTransaction>();
		List<InvoiceTransaction> invoiceTransactions = new ArrayList<InvoiceTransaction>();
		if (date == null) {
			invoiceTransactions = em.createNamedQuery("invoice-with-seller-store")
					.setParameter("seller", seller).getResultList();
		} else {
			invoiceTransactions = em.createNamedQuery("invoice-with-seller-store-and-date")
					.setParameter("seller", seller)
					.setParameter("date", date).getResultList();
		}
		if (includeDetails) {
			for (InvoiceTransaction transaction : invoiceTransactions)
				Hibernate.initialize(transaction.getTransactions());
		}
		return invoiceTransactions;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceTransaction> getInvoiceForSellerWithinDate(Store seller, Date start, Date end) {
		if(seller == null) return new ArrayList<InvoiceTransaction>();
		List<InvoiceTransaction> invoiceTransactions = new ArrayList<InvoiceTransaction>();
		if (start == null)
			return invoiceTransactions;
		if (end == null)
			end = new Date();
		try {
			invoiceTransactions = em.createNamedQuery("invoice-with-seller-store-and-date-range")
					.setParameter("seller", seller).setParameter("start", start)
					.setParameter("end", end).getResultList();
		} catch (Exception e) {
			log.error("can not populate invoice-with-seller-store-and-date-range query", e);
		}
		return invoiceTransactions;
	}

	private Element createPhoneNumber(String string) {
		return new DefaultElement("phoneNumber").addText(string);
	}

	@SuppressWarnings("unused")
	private Element createStreetAddressTwo(String string) {
		return new DefaultElement("streetAddressTwo").addText(string);
	}

	private Element createStreetAddressOne(String string) {
		return new DefaultElement("streetAddressOne").addText(string);
	}

	private Element createNameElement(String text) {
		return new DefaultElement("name").addText(text);
	}

	private Element createTradeItemElementInformationType(String text) {
		return new DefaultElement("tradeItemInformationType").addText(text);
	}

	private Element createTradeItemElementInformationValue(String text) {
		return new DefaultElement("tradeItemInformationValue").addText(text);
	}

	private Element createTradeItemAdditionalInformation(String type, String value) {
		Element tradeItemInformation = new DefaultElement("tradeItemInformation");
		tradeItemInformation.add(createTradeItemElementInformationType(type));
		tradeItemInformation.add(createTradeItemElementInformationValue(value));
		return tradeItemInformation;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceTransaction> getInvoicesFromSellerStoreWithAdvanceSearchCriteria(Store store, Product product, DocumentType documentType) {
		if(store == null || product == null) return new ArrayList<InvoiceTransaction>();
		List<InvoiceTransaction> invoiceTransactions = new ArrayList<InvoiceTransaction>();
		try {
			invoiceTransactions = em.createNamedQuery("invoice-with-seller-store-and-product")
					.setParameter("seller", store)
					.setParameter("productUuid", product.getUuid())
					.setParameter("documentType", documentType).getResultList();
		} catch (Exception e) {
			log.error("can not populate invoice-with-seller-store-and-date-range query", e);
		}
		return invoiceTransactions;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceTransaction> getInvoicesFromBuyerStoreWithAdvanceSearchCriteria(Store store, Product product, DocumentType documentType) {
		if(store == null || product == null) return new ArrayList<InvoiceTransaction>();
		List<InvoiceTransaction> invoiceTransactions = new ArrayList<InvoiceTransaction>();
		try {
			invoiceTransactions = em.createNamedQuery("invoice-with-buyer-store-and-product")
					.setParameter("buyer", store)
					.setParameter("productUuid", product.getUuid())
					.setParameter("documentType", documentType).getResultList();
		} catch (Exception e) {
			log.error("can not populate invoice-with-seller-store-and-date-range query", e);
		}
		return invoiceTransactions;
	}

	public InvoiceTransaction populateInvoiceDetail(InvoiceTransaction invoiceTransaction) {
		InvoiceTransaction transaction = em.find(InvoiceTransaction.class, invoiceTransaction.getUuid());
		Hibernate.initialize(transaction.getTransactions());
		return transaction;
	}
}
