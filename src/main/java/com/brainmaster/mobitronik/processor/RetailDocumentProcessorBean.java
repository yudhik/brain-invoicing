package com.brainmaster.mobitronik.processor;

import com.brainmaster.mobitronik.bean.service.RetailDocumentService;
import com.brainmaster.mobitronik.model.RetailDocument;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RetailDocumentProcessorBean implements RetailDocumentProcessor {

	@Autowired
	private RetailDocumentService retailDocumentService;

	@Override
	public String getDocumentId(String invoiceNumber) {
		return retailDocumentService.getDocumentId(invoiceNumber);
	}

	@Override
	public RetailDocument find(UUID uuid, boolean includeContent) {
		return retailDocumentService.find(uuid, includeContent);
	}

}
