package com.brainmaster.mobitronik.dto;

import java.math.BigDecimal;

import com.brainmaster.mobitronik.model.ProductStore;

public class StockReportData extends ProductStoreData {

	private static final long serialVersionUID = 4918644968667959471L;

	private BigDecimal averagePrice;
	private Integer currentQuantity;

	public StockReportData() {
	}

	public StockReportData(ProductData product, StoreData store, Integer quantity, BigDecimal averagePrice, Integer currentQuantity) {
		super(product, store, quantity);
		this.setAveragePrice(averagePrice);
		this.setCurrentQuantity(currentQuantity);
	}

	public StockReportData(ProductStore productStore, BigDecimal averagePrice, Integer currentQuantity) {
		super(ProductData.convertProduct(productStore.getProduct()), StoreData.convertStore(productStore.getStore()),
				productStore.getQuantity());
		this.setAveragePrice(averagePrice);
		this.setCurrentQuantity(currentQuantity);
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public static StockReportData convert(ProductStore productStore, BigDecimal averagePrice, Integer currentQuantity) {
		return new StockReportData(productStore, averagePrice, currentQuantity);
	}

	public Integer getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

}
