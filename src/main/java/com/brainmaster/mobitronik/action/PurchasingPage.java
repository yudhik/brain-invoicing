package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.brainmaster.mobitronik.dto.BrandData;
import com.brainmaster.mobitronik.dto.CategoryData;
import com.brainmaster.mobitronik.dto.InvoiceData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.primefaces.model.InvoiceDataModel;
import com.brainmaster.mobitronik.primefaces.model.ProductDataModel;
import com.brainmaster.mobitronik.processor.ProductProcessor;
import com.brainmaster.mobitronik.processor.PurchasingProcessor;
import com.brainmaster.mobitronik.processor.RetailDocumentProcessor;
import com.brainmaster.mobitronik.processor.StoreProcessor;

@Component
@Scope("request")
public class PurchasingPage {

	private static final Logger log = LoggerFactory.getLogger(PurchasingPage.class);

	@Autowired
	private PurchasingProcessor purchasingProcessor;

	@Autowired
	private PurchasingForm purchasingForm;

	@Autowired
	private StoreProcessor storeProcessor;

	@Autowired
	private ProductProcessor productProcessor;

	@Autowired
	private RetailDocumentProcessor retailDocumentProcessor;

	@Autowired
	private ActiveUser activeUser;

	@Autowired
	private PurchaseAdvancedSearchForm advancedSearchForm;

	private StoreData buyerStore;
	private UUID documentId;

	public List<BrandData> getBrands() {
		List<BrandData> brands = new ArrayList<BrandData>();
		for (ProductData product : getProducts()) {
			if (!brands.contains(product.getBrand()))
				brands.add(product.getBrand());
		}
		return brands;
	}

	public List<CategoryData> getCategories() {
		List<CategoryData> categories = new ArrayList<CategoryData>();
		for (ProductData product : getProducts()) {
			if (!categories.contains(product.getCategory()))
				categories.add(product.getCategory());
		}
		return categories;
	}

	public void saveDetail() {
		purchasingForm.addDetail();
	}

	public void newPurchase() {
		purchasingForm.createNewPurchase();
	}

	public List<StoreData> getStores() {
		return storeProcessor.getStores();
	}

	public List<ProductData> getProducts() {
		return productProcessor.getProductForStore(activeUser
				.getSelectedStore());
	}

	public List<StoreData> completeStore(String query) {
		List<StoreData> result = new ArrayList<StoreData>();
		for (StoreData storeData : getStores()) {
			if (storeData.getStoreName().startsWith(query))
				result.add(storeData);
		}
		return result;
	}

	public List<ProductData> completeProduct(String query) {
		List<ProductData> result = new ArrayList<ProductData>();
		for (ProductData productData : getProducts()) {
			if (productData.getProductCode().startsWith(query))
				result.add(productData);
		}
		return result;
	}

	public PurchaseAdvancedSearchForm getAdvancedSearchForm() {
		return advancedSearchForm;
	}

	public void setAdvancedSearchForm(PurchaseAdvancedSearchForm advancedSearchForm) {
		this.advancedSearchForm = advancedSearchForm;
	}

	public void setPurchasingForm(PurchasingForm purchasingForm) {
		this.purchasingForm = purchasingForm;
	}

	public PurchasingForm getPurchasingForm() {
		return purchasingForm;
	}

	public StoreData getBuyerStore() {
		if (buyerStore == null) {
			setBuyerStore(activeUser.getSelectedStore());
		}
		return buyerStore;
	}

	public void setBuyerStore(StoreData buyerStore) {
		this.buyerStore = buyerStore;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public void saveInvoice(ActionEvent actionEvent) {
		try {
			log.debug("try saving purchase");
			purchasingForm.getPurchaseData().setDocumentType(DocumentType.PURCHASE_INVOICE);
			documentId = purchasingProcessor.savePurchasing(activeUser.getSelectedStore(), purchasingForm.getPurchaseData());
		} catch (Exception e) {
			log.error("error saving purchase", e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "cannot saving purchasing", e.getMessage()));
		}
		log.debug("out from saving purchase");
		if (documentId != null) {
			log.debug("possible to continue");
		} else {
			log.debug("not possible to continue");
		}
	}

	public List<InvoiceData> getInvoiceList() {
		if (!purchasingForm.isUseAdvancedSearch())
			return purchasingProcessor.getPurchasingList(purchasingForm.getSelectedDate(), activeUser.getSelectedStore());
		return purchasingProcessor.getPurchasingList(purchasingForm.getSelectedDate(), activeUser.getSelectedStore(), advancedSearchForm);
	}

	public InvoiceDataModel getInvoiceDataModel() {
		return new InvoiceDataModel(getInvoiceList());
	}

	public String getTransformationResult() {
		return purchasingProcessor.getTransformationResult(purchasingForm.getUuidParam(), purchasingForm.getDocumentTypeParam());
	}

	public String getInvoiceResult() {
		Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		purchasingForm.setUuidParam(requestMap.get("uuid"));
		purchasingForm.setDocumentTypeParam(requestMap.get("documentType"));
		newPurchase();
		return "show_purchaseDetail";
	}

	public String getPopulateInvoiceResult() {
		if (purchasingForm.getSelectedPurchase() != null) {
			purchasingForm.setUuidParam(retailDocumentProcessor.getDocumentId(purchasingForm.getSelectedPurchase().getInvoiceNumber()));
			purchasingForm.setDocumentTypeParam(DocumentType.PURCHASE_INVOICE.toString());
			return "show_purchaseDetail";
		}
		return "";
	}

	public void updateTipeChange(SelectEvent event) {
		getPurchasingForm().setSearchBy((String) event.getObject());
	}

	public void updateDetailProductSelection(SelectEvent event) {
		getPurchasingForm().getDetail().setProduct(
				(ProductData) event.getObject());
	}

	public ProductDataModel getProductSearchResult() {
		List<ProductData> productsData = new ArrayList<ProductData>();
		if (getPurchasingForm() != null && getPurchasingForm().getSearchTerms() != null
				&& (!StringUtils.isEmpty(getPurchasingForm().getSearchTerms().trim())
				|| !StringUtils.isEmpty(getPurchasingForm().getSearchBy().trim()))) {
			String terms = getPurchasingForm().getSearchTerms();
			for (ProductData product : getProducts()) {
				if (getPurchasingForm().isCategoryTerms()) {
					if (product.getCategory().getUuid().equalsIgnoreCase(terms))
						productsData.add(product);
				} else if (getPurchasingForm().isBrandTerms()) {
					if (product.getBrand().getUuid().equalsIgnoreCase(terms))
						productsData.add(product);
				} else if (getPurchasingForm().getSearchBy().equalsIgnoreCase("code")) {
					if (product.getProductCode().contains(terms))
						productsData.add(product);
				} else {
					if (product.getProductName().contains(terms))
						productsData.add(product);
				}
			}
		}
		return new ProductDataModel(productsData);
	}
}
