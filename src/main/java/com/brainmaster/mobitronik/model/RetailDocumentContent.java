package com.brainmaster.mobitronik.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import com.brainmaster.util.DatabaseColumnConstant;

@Entity
@Table(name = "retaildocumentcontent")
public class RetailDocumentContent implements Serializable {

	private static final long serialVersionUID = -5475525508353708560L;

	@Id
	@Type(type = "uuid")
	@Column(length = DatabaseColumnConstant.SIZE_UUID)
	private UUID uuid;

	@Lob
	@Column(nullable = false)
	@ColumnTransformer(read = "UNCOMPRESS(data)", write = "COMPRESS(?)")
	private Byte[] data;

	@OneToOne
	private RetailDocument retailDocument;

	public RetailDocumentContent() {
	}

	public RetailDocumentContent(UUID uuid, Byte[] data) {
		this.uuid = uuid;
		this.data = data;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Byte[] getData() {
		return data;
	}

	@Transient
	public Source getDataAsSource() throws Exception {
		return new StreamSource(new InputStreamReader(new BufferedInputStream(
				new ByteArrayInputStream(ArrayUtils.toPrimitive(data)))));
	}

	public void setData(Byte[] data) {
		this.data = data;
	}

	public RetailDocument getRetailDocument() {
		return retailDocument;
	}

	public void setRetailDocument(RetailDocument retailDocument) {
		this.retailDocument = retailDocument;
	}

}
