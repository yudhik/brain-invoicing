package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@Table(name = "invoicetransactiondetail")
public class InvoiceTransactionDetail implements Serializable {

	private static final long serialVersionUID = 1873987105099997645L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Column(name = "product_id")
	@Type(type = "uuid")
	private UUID product;

	private String gtin;

	@Column(nullable = false, columnDefinition = "Decimal(25,2) default '0.00'")
	private BigDecimal price;

	@Column(name = "serial_number", nullable = false, columnDefinition = "varchar(255) default '-'")
	private String serialNumber;

	@Column(nullable = false, columnDefinition = "Decimal(7) default '0'")
	private Integer quantity;

	@ManyToOne
	private InvoiceTransaction invoiceTransaction;

	public InvoiceTransactionDetail() {
	}

	public InvoiceTransactionDetail(UUID uuid, UUID product, BigDecimal price, String serialNumber) {
		this.uuid = uuid;
		this.product = product;
		this.price = price;
		this.serialNumber = serialNumber;
	}

	public InvoiceTransactionDetail(UUID uuid, UUID product, BigDecimal price, String serialNumber, String gtin) {
		this.uuid = uuid;
		this.product = product;
		this.price = price;
		this.serialNumber = serialNumber;
		this.gtin = gtin;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getProduct() {
		return product;
	}

	public void setProduct(UUID product) {
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

	public InvoiceTransaction getInvoiceTransaction() {
		return invoiceTransaction;
	}

	public void setInvoiceTransaction(InvoiceTransaction invoiceTransaction) {
		this.invoiceTransaction = invoiceTransaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((invoiceTransaction == null) ? 0 : invoiceTransaction
						.hashCode());
		result = prime * result
				+ ((serialNumber == null) ? 0 : serialNumber.hashCode());
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
		InvoiceTransactionDetail other = (InvoiceTransactionDetail) obj;
		if (invoiceTransaction == null) {
			if (other.invoiceTransaction != null)
				return false;
		} else if (!invoiceTransaction.equals(other.invoiceTransaction))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceTransactionDetail [product=" + product + ", price="
				+ price + ", serialNumber=" + serialNumber + "]";
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
