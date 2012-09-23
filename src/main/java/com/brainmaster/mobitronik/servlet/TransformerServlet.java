package com.brainmaster.mobitronik.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.brainmaster.mobitronik.bean.service.RetailDocumentService;
import com.brainmaster.mobitronik.model.DocumentType;
import com.brainmaster.mobitronik.model.RetailDocument;
import com.brainmaster.util.helper.uuid.UUIDHelper;

@Component("transformer")
public class TransformerServlet implements HttpRequestHandler {

	private static final Logger log = LoggerFactory.getLogger(TransformerServlet.class);

	@Autowired
	private RetailDocumentService retailDocumentService;

	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private static final Long RELOAD_EVERY = 3000L;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UUID uuid = null;
		boolean timeoutCondition = false;
		log.info("i get invoke, check the event");
		try {
			if (request.getParameter("uuid") == null)
				throw new Exception("uuid is null");
			uuid = UUIDHelper.stringToUUID(request.getParameter("uuid"));
		} catch (Exception e) {
			log.warn("uuid incorrect");
			response.sendRedirect(request.getContextPath());
		}
		if (uuid != null && request.getParameter("documentType") != null
				&& DocumentType.INVOICE.toString().equalsIgnoreCase(request.getParameter("documentType"))) {
			try {
				InputStream templateIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("/stylesheets/invoice.xslt");
				RetailDocument document = null;
				try {
					Long start = new Date().getTime();
					while (document == null || timeoutCondition == false) {
						document = retailDocumentService.find(uuid, true);
						timeoutCondition = checkTimeout(start);
					}
				} catch (Exception e) {
					log.error("something is not right here", e);
				}
				Source template = new StreamSource(templateIn);
				Source source = document.getDocumentContent().getDataAsSource();
				Result result = new StreamResult(response.getOutputStream());
				transformerFactory.newTransformer(template).transform(source, result);
			} catch (Exception e) {
				log.error("cannot generate output", e);
			}
		}
	}

	private boolean checkTimeout(Long start) {
		if (new Date().getTime() - start >= RELOAD_EVERY) {
			log.debug("reload data");
			return true;
		}
		return false;
	}

}
