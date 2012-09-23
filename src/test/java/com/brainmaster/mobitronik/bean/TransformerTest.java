package com.brainmaster.mobitronik.bean;

import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformerTest {

	private static final Logger log = LoggerFactory.getLogger(TransformerTest.class);
	private static final String OUTPUT_FILE_NAME = "target/invoice.html";
	private TransformerFactory transformerFactory;

	@Before
	public void initTransformer() {
		transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
	}

	@Test
	public void testTransformingInvoice() throws Exception {
		File fileout = new File(OUTPUT_FILE_NAME);
		InputStream templateIn = this.getClass().getResourceAsStream("/invoice.xslt");
		InputStream sourceIn = this.getClass().getResourceAsStream("/test.xml");
		Assert.assertNotNull(templateIn);
		Assert.assertNotNull(sourceIn);
		Source template = new StreamSource(templateIn);
		Source source = new StreamSource(sourceIn);
		Result result = new StreamResult(fileout);
		Transformer transformer = transformerFactory.newTransformer(template);
		transformer.transform(source, result);
		log.info("transform has been finish, please check the target directory");
	}

}
