package com.brainmaster.mobitronik.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@NamedQueries({@NamedQuery(name = "documentid-with-invoice-reference", query = "from RetailDocument a where a.reference = :reference")})
@Table(name = "retaildocument")
public class RetailDocument implements Serializable {

	private static final long serialVersionUID = 8668867846468149644L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	//	@ManyToOne
	//	@JoinColumn(name = "sender_store", nullable = false, updatable = false)
	@Type(type = "uuid")
	private UUID senderStore;

	//	@ManyToOne
	//	@JoinColumn(name = "recipient_store", nullable = false, updatable = false)
	@Type(type = "uuid")
	private UUID recipientStore;

	@Column(length = DatabaseColumnConstant.SIZE_DEFAULT)
	private String reference;

	@Column(name = "document_type")
	private DocumentType documentType;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "retailDocument")
	@PrimaryKeyJoinColumn
	private RetailDocumentContent documentContent;

	public RetailDocument() {
	}

	public RetailDocument(UUID uuid, UUID senderStore, UUID recipientStore,
			String reference, DocumentType documentType, Date dateCreated,
			RetailDocumentContent documentContent) {
		this.uuid = uuid;
		this.senderStore = senderStore;
		this.recipientStore = recipientStore;
		this.reference = reference;
		this.documentType = documentType;
		this.dateCreated = dateCreated;
		this.documentContent = documentContent;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getSenderStore() {
		return senderStore;
	}

	public void setSenderStore(UUID senderStore) {
		this.senderStore = senderStore;
	}

	public UUID getRecipientStore() {
		return recipientStore;
	}

	public void setRecipientStore(UUID recipientStore) {
		this.recipientStore = recipientStore;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDocumentContent(RetailDocumentContent documentContent) {
		this.documentContent = documentContent;
	}

	public RetailDocumentContent getDocumentContent() {
		return documentContent;
	}

}
