package com.brainmaster.mobitronik.action;

import java.util.ArrayList;
import java.util.List;
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
import com.brainmaster.mobitronik.dto.InvoiceDetailData;
import com.brainmaster.mobitronik.dto.ProductData;
import com.brainmaster.mobitronik.dto.StoreData;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.primefaces.model.InvoiceDataModel;
import com.brainmaster.mobitronik.primefaces.model.ProductDataModel;
import com.brainmaster.mobitronik.processor.InvoiceProcessor;
import com.brainmaster.mobitronik.processor.ProductProcessor;
import com.brainmaster.mobitronik.processor.RetailDocumentProcessor;
import com.brainmaster.mobitronik.processor.StoreProcessor;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component
@Scope("request")
public class InvoicingPage {

	private static final Logger log = LoggerFactory.getLogger(InvoicingPage.class);

	@Autowired
	private InvoiceProcessor invoiceProcessor;

	@Autowired
	private InvoiceForm invoiceForm;

	@Autowired
	private StoreProcessor storeProcessor;

	@Autowired
	private ProductProcessor productProcessor;

	@Autowired
	private RetailDocumentProcessor retailDocumentProcessor;

	@Autowired
	private ActiveUser activeUser;

	@Autowired
	private InvoiceAdvancedSearchForm advancedSearchForm;

	private StoreData sellerStore;
	private UUID documentId;

	public List<StoreData> getStores() {
		return storeProcessor.getStores();
	}

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

	public void saveInvoice(ActionEvent actionEvent) {
		try {
			log.debug("try saving invoice");
			invoiceForm.getInvoiceData().setDocumentType(DocumentType.INVOICE);
			getInvoiceForm().setUuidParam(UUIDHelper.uuidToString(invoiceProcessor.saveInvoice(activeUser.getSelectedStore(), getInvoiceForm().getInvoiceData())));
		} catch (Exception e) {
			log.error("error saving invoice", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "cannot saving invoice", e.getMessage()));
		}
		log.debug("out from saving invoice");
		if (getInvoiceForm().getUuidParam() != null) {
			log.debug("possible to continue");
		} else {
			log.debug("not possible to continue");
		}
	}

	public String getInvoiceResult() {
//		Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//		invoiceForm.setUuidParam(requestMap.get("uuid"));
		invoiceForm.setDocumentTypeParam(DocumentType.INVOICE.toString());
		newInvoice();
		return "show_invoiceDetail";
	}

	public String getPopulateInvoiceResult() {
		if (invoiceForm.getSelectedInvoice() != null) {
			invoiceForm.setUuidParam(retailDocumentProcessor.getDocumentId(invoiceForm.getSelectedInvoice().getInvoiceNumber()));
			invoiceForm.setDocumentTypeParam(DocumentType.INVOICE.toString());
			return "show_invoiceDetail";
		}
		return "";
	}

	public void saveDetail() {
		invoiceForm.addDetail();
	}

	public void newInvoice() {
		invoiceForm.createNewInvoice();
	}

	public List<ProductData> getProducts() {
		return productProcessor.getProductForStore(activeUser
				.getSelectedStore());
	}

	public String getDocumentId() {
		return UUIDHelper.uuidToString(documentId);
	}

	public List<InvoiceData> getInvoiceList() {
		if (!invoiceForm.isUseAdvancedSearch()) {
			return invoiceProcessor.getInvoiceList(invoiceForm.getSelectedDate(), activeUser.getSelectedStore());
		}
		return invoiceProcessor.getInvoiceList(invoiceForm.getSelectedDate(), activeUser.getSelectedStore(), advancedSearchForm, DocumentType.INVOICE);
	}

	public InvoiceDataModel getInvoiceDataModel() {
		return new InvoiceDataModel(getInvoiceList());
	}

	public InvoiceAdvancedSearchForm getAdvancedSearchForm() {
		return advancedSearchForm;
	}

	public void setAdvancedSearchForm(InvoiceAdvancedSearchForm advancedSearchForm) {
		this.advancedSearchForm = advancedSearchForm;
	}

	public InvoiceForm getInvoiceForm() {
		return invoiceForm;
	}

	public void setInvoiceForm(InvoiceForm invoiceForm) {
		this.invoiceForm = invoiceForm;
	}

	public String getTransformationResult() {
		return invoiceProcessor.getTransformationResult(invoiceForm.getUuidParam(), invoiceForm.getDocumentTypeParam());
	}

	public StoreData getSellerStore() {
		if (sellerStore == null) {
			setSellerStore(activeUser.getSelectedStore());
		}
		return sellerStore;
	}

	public void setSellerStore(StoreData sellerStore) {
		this.sellerStore = sellerStore;
	}

	public void updateTipeChange(SelectEvent event) {
		getInvoiceForm().setSearchBy((String) event.getObject());
	}

	public void updateInvoiceDetail() {
		
	}
	
	public List<InvoiceDetailData> getDetailInvoices() {
//		List<InvoiceDetailData> datas = new ArrayList<InvoiceDetailData>();
//		for(InvoiceDetailData detailData : getInvoiceForm().getInvoiceData().getInvoiceDetails()) {
//			datas
//		}
		return getInvoiceForm().getInvoiceData().getInvoiceDetails();
	}
	
	public void updateDetailProductSelection(SelectEvent event) {
		getInvoiceForm().getDetail()
				.setProduct((ProductData) event.getObject());
	}

	public void useAdvanceSearchForm() {
		getInvoiceForm().setUseAdvancedSearch(true);
	}
	
	public void useDateSearch() {
		getInvoiceForm().setUseAdvancedSearch(false);
	}
	
	public ProductDataModel getProductSearchResult() {
		List<ProductData> productsData = new ArrayList<ProductData>();
		if (getInvoiceForm() != null && getInvoiceForm().getSearchTerms() != null
				&& (!StringUtils.isEmpty(getInvoiceForm().getSearchTerms().trim())
				|| !StringUtils.isEmpty(getInvoiceForm().getSearchBy().trim()))) {
			String terms = getInvoiceForm().getSearchTerms();
			for (ProductData product : getProducts()) {
				if (getInvoiceForm().isCategoryTerms()) {
					if (product.getCategory().getUuid().equalsIgnoreCase(terms))
						productsData.add(product);
				} else if (getInvoiceForm().isBrandTerms()) {
					if (product.getBrand().getUuid().equalsIgnoreCase(terms))
						productsData.add(product);
				} else if (getInvoiceForm().getSearchBy().equalsIgnoreCase("code")) {
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
	
	public void changeDetail() {
		if(getInvoiceForm().getMistakeId()!=null)
			for(InvoiceDetailData detail : getInvoiceForm().getInvoiceData().getInvoiceDetails()) {
				if(detail.getUuid().equals(getInvoiceForm().getMistakeId()))
				getInvoiceForm().setDetail(detail);
			}
	}
	
	public void saveUpdateAndGenerateNewDetail() {
		if(getInvoiceForm().getMistakeId() != null) {
			getInvoiceForm().createNewDetail();
			getInvoiceForm().setMistakeId(null);
		}
	}
	
	public void removeDetail() {
		if(getInvoiceForm().getMistakeId() != null) {
			List<InvoiceDetailData> modificationDetail = new ArrayList<InvoiceDetailData>();
			for(InvoiceDetailData detail : getInvoiceForm().getInvoiceData().getInvoiceDetails()) {
				if(!detail.getUuid().equals(getInvoiceForm().getMistakeId()))
					modificationDetail.add(detail);
			}
			getInvoiceForm().getInvoiceData().setInvoiceDetails(modificationDetail);
			getInvoiceForm().setMistakeId(null);
			getInvoiceForm().createNewDetail();
		}
	}
}
