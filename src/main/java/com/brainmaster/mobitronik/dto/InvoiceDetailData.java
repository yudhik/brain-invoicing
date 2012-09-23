package com.brainmaster.mobitronik.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.brainmaster.mobitronik.model.InvoiceTransactionDetail;

public class InvoiceDetailData implements Serializable {

	private static final long serialVersionUID = -7463047749438583199L;

	private UUID uuid;

	private ProductData product;

	private BigDecimal price;

	private String serialNumber = "-";

	private Integer quantity = 0;

	public InvoiceDetailData() {
		uuid = UUID.randomUUID();
	}

	public InvoiceDetailData(UUID uuid) {
		this.uuid = uuid;
	}

	public InvoiceDetailData(UUID uuid, ProductData product, BigDecimal price,
			String serialNumber) {
		this.uuid = uuid;
		this.product = product;
		this.price = price;
		this.serialNumber = serialNumber;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public ProductData getProduct() {
		return product;
	}

	public void setProduct(ProductData product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public static InvoiceDetailData convertInvoiceTransaction(
			InvoiceTransactionDetail invoiceTransaction) {
		InvoiceDetailData detailData = new InvoiceDetailData(invoiceTransaction
				.getUuid(), new ProductData(invoiceTransaction.getProduct()),
				invoiceTransaction.getPrice(), invoiceTransaction
						.getSerialNumber());
		detailData.setQuantity(invoiceTransaction.getQuantity());
		return detailData;
	}

	public InvoiceTransactionDetail getInvoiceTransactionEntity() {
		InvoiceTransactionDetail detailTransaction = new InvoiceTransactionDetail(
				uuid, product.getUuid(), price, serialNumber);
		detailTransaction.setQuantity(quantity);
		return detailTransaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceDetailData other = (InvoiceDetailData) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceDetailData [uuid=" + uuid + ", product=" + product + ", price=" + price + ", serialNumber=" + serialNumber
				+ ", quantity=" + quantity + "]";
	}

	public Integer getJumlah() {
		if (serialNumber.equals("-"))
			return quantity;
		return serialNumber.split(",").length;
	}

	public BigDecimal getTotal() {
		if (getJumlah() < 1 || price == null)
			return BigDecimal.ZERO;
		return price.multiply(new BigDecimal(getJumlah()));
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
