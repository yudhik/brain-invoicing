package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "productstorecalculationlog")
public class ProductStoreCalculationLog implements Serializable {

	private static final long serialVersionUID = 3197607927792557021L;

	@Id
	@Type(type = "uuid")
	@Column(name = "calculation_log_id")
	private UUID calculationLogId;

	@ManyToOne(targetEntity = ProductStoreTransaction.class)
	private ProductStoreTransaction latestProductStoreTransaction;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "calculation_time")
	private Date calculationTime;

	@Column(name = "original_quantity")
	private Long originalQuantity;

	@Column(name = "mathematic_operand")
	private char mathematicOperand;

	private Long result;

	public ProductStoreCalculationLog() {
	}

	public ProductStoreCalculationLog(UUID calculationLogId,
			ProductStoreTransaction latestProductStoreTransaction,
			Date calculationTime, Long originalQuantity,
			char mathematicOperand, Long result) {
		this.calculationLogId = calculationLogId;
		this.latestProductStoreTransaction = latestProductStoreTransaction;
		this.calculationTime = calculationTime;
		this.originalQuantity = originalQuantity;
		this.mathematicOperand = mathematicOperand;
		this.result = result;
	}

	public UUID getCalculationLogId() {
		return calculationLogId;
	}

	public void setCalculationLogId(UUID calculationLogId) {
		this.calculationLogId = calculationLogId;
	}

	public ProductStoreTransaction getLatestProductStoreTransaction() {
		return latestProductStoreTransaction;
	}

	public void setLatestProductStoreTransaction(ProductStoreTransaction latestProductStoreTransaction) {
		this.latestProductStoreTransaction = latestProductStoreTransaction;
	}

	public Date getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(Date calculationTime) {
		this.calculationTime = calculationTime;
	}

	public Long getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(Long originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public char getMathematicOperand() {
		return mathematicOperand;
	}

	public void setMathematicOperand(char mathematicOperand) {
		this.mathematicOperand = mathematicOperand;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ProductStoreCalculationLog [calculationLogId="
				+ calculationLogId + ", productStoreTransaction="
				+ latestProductStoreTransaction + ", calculationTime="
				+ calculationTime + ", originalQuantity=" + originalQuantity
				+ ", mathematicOperand=" + mathematicOperand + ", result="
				+ result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((calculationLogId == null) ? 0 : calculationLogId.hashCode());
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
		ProductStoreCalculationLog other = (ProductStoreCalculationLog) obj;
		if (calculationLogId == null) {
			if (other.calculationLogId != null)
				return false;
		} else if (!calculationLogId.equals(other.calculationLogId))
			return false;
		return true;
	}

}
