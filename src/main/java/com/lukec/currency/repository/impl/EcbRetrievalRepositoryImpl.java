package com.lukec.currency.repository.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lukec.currency.bo.CurrencyConversion;
import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.repository.EcbRetrievalRepository;
import com.lukec.currency.repository.UrlReader;

import java.io.File;
import javax.xml.bind.*;

@Service
public class EcbRetrievalRepositoryImpl implements EcbRetrievalRepository {
	private Logger logger = LoggerFactory.getLogger(EcbRetrievalRepositoryImpl.class);

	private UrlReader reader;

	public EcbRetrievalRepositoryImpl(UrlReader reader) {
		this.reader = reader;
	}

	@Value("${eurofx.daily}")
	private String dailyFxUrl;
	
	@Value("${eurofx.historical}")
	private String archiveFxUrl;

	@Override
	public CurrencyConversionEntry euroFxDaily() throws DocumentRetrievalException {
		logger.warn("Daily URL="+dailyFxUrl);
		String content = reader.readUrl(dailyFxUrl);
		logger.warn("Content is "+content);
		Document doc = convertStringToXMLDocument(content);

		logger.debug(content);
		List<CurrencyConversionEntry> listResults = new ArrayList<>();
		
		try {
			listResults = getInformation(doc);
		} catch (XPathExpressionException e) {
			throw new DocumentRetrievalException(e.getLocalizedMessage());
		}
		CurrencyConversionEntry curr = listResults.get(0);
		return curr;
	}
	
	@Override
	public List<CurrencyConversionEntry> euroFxHistory() throws DocumentRetrievalException {
		logger.warn("archiveFxUrl="+archiveFxUrl);
		String content = reader.readUrl(archiveFxUrl);
		logger.warn("Content is "+content);
		Document doc = convertStringToXMLDocument(content);

		logger.debug(content);
		List<CurrencyConversionEntry> listResults = new ArrayList<>();
		
		try {
			listResults = getInformation(doc);
		} catch (XPathExpressionException e) {
			throw new DocumentRetrievalException(e.getLocalizedMessage());
		}
		return listResults;
	}

	private List<CurrencyConversionEntry> getInformation(Document doc) throws XPathExpressionException {
		List<CurrencyConversionEntry> listResults = new ArrayList<>();
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile("//Cube/Cube[@time]");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			List<CurrencyConversion> list = new ArrayList<>();
			Node n = nodes.item(i);
			NodeList nodesChildren = n.getChildNodes();
			String theDate = n.getAttributes().getNamedItem("time").getNodeValue();
			logger.debug("Date received was "+theDate+" length is "+nodesChildren.getLength());
			for (int j = 0; j < nodesChildren.getLength(); j++) {
				Node child = nodesChildren.item(j);
				if(child.getAttributes() != null) {
					String currency = child.getAttributes().getNamedItem("currency").getNodeValue();
					String rate = child.getAttributes().getNamedItem("rate").getNodeValue();
					BigDecimal rateBig = new BigDecimal(rate);
					CurrencyConversion conversion = new CurrencyConversion(currency, currency, rateBig);
					list.add(conversion);
				}
			}
			CurrencyConversionEntry curr = new CurrencyConversionEntry(theDate, list);
			listResults.add(curr);
		}
		return listResults;
	}

	private Document convertStringToXMLDocument(String xmlString) throws DocumentRetrievalException {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			// Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			// Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			throw new DocumentRetrievalException(e.getLocalizedMessage());
		}
	}

}
