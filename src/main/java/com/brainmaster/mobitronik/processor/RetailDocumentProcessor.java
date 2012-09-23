package com.brainmaster.mobitronik.processor;

import java.util.UUID;

import com.brainmaster.mobitronik.model.RetailDocument;

public interface RetailDocumentProcessor {

	public String getDocumentId(String invoiceNumber);

	public RetailDocument find(UUID stringToUUID, boolean includeContent);

}
