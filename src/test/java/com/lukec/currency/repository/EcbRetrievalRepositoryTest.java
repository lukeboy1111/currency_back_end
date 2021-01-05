package com.lukec.currency.repository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.lukec.currency.bo.CurrencyConversion;
import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.repository.impl.EcbRetrievalRepositoryImpl;


@RunWith(MockitoJUnitRunner.class)
public class EcbRetrievalRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(EcbRetrievalRepositoryTest.class);
	
	@InjectMocks
	@Spy
	EcbRetrievalRepositoryImpl service;
	
	@Mock
	private UrlReader reader;
	
	private String xmlContent;
	
	private String applicationFile = "example.xml";
	
	private String applicationHistoryFile = "example2.xml";
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void testXmlRead() throws DocumentRetrievalException {
		xmlContent = asString(applicationFile);
		
		Mockito.when(reader.readUrl(Mockito.any())).thenReturn(xmlContent);
		logger.debug(xmlContent);
		CurrencyConversionEntry currencies = service.euroFxDaily();
		assertThat(currencies).isNotNull();
		assertThat(currencies.getTheDate()).isEqualTo("2020-12-31");
		assertThat(currencies.getListCurrencies().size()).isNotZero();
		int theSize = currencies.getListCurrencies().size();
		assertThat(theSize).isEqualTo(32);
		CurrencyConversion c = currencies.getListCurrencies().get(0);
		assertThat(c).isNotNull();
		assertThat(c.getCurrency()).isEqualTo("USD");
		assertThat(c.getRate().toPlainString()).isEqualTo("1.2271");
	}
	
	@Test(expected = DocumentRetrievalException.class)
	public void testXmlReadFail() throws DocumentRetrievalException {
		xmlContent="<Wrong><Bob>";
		Mockito.when(reader.readUrl(Mockito.any())).thenReturn(xmlContent);
		CurrencyConversionEntry currencies = service.euroFxDaily();
	}
	
	@Test
	public void testXmlReadHistory() throws DocumentRetrievalException {
		xmlContent = asString(applicationHistoryFile);
		
		Mockito.when(reader.readUrl(Mockito.any())).thenReturn(xmlContent);
		logger.debug(xmlContent);
		List<CurrencyConversionEntry> list = service.euroFxHistory();
		assertThat(list).isNotNull();
		assertThat(list.size()).isEqualTo(63);
		CurrencyConversionEntry currencies = list.get(0);
		assertThat(currencies.getListCurrencies().size()).isNotZero();
		assertThat(currencies.getTheDate()).isEqualTo("2020-12-31");
		int theSize = currencies.getListCurrencies().size();
		assertThat(theSize).isEqualTo(32);
		CurrencyConversion c = currencies.getListCurrencies().get(0);
		assertThat(c).isNotNull();
		assertThat(c.getCurrency()).isEqualTo("USD");
		assertThat(c.getRate().toPlainString()).isEqualTo("1.2271");
	}
	
	@Test(expected = DocumentRetrievalException.class)
	public void testXmlReadHistoryFail() throws DocumentRetrievalException {
		xmlContent="<Wrong><Bob>";
		Mockito.when(reader.readUrl(Mockito.any())).thenReturn(xmlContent);
		List<CurrencyConversionEntry> list = service.euroFxHistory();
	}
	
	
	private String asString(String fileName) {
		StringBuffer xml = new StringBuffer();
		Resource resourceF = new ClassPathResource(fileName);

	    try {
			InputStream input = resourceF.getInputStream();
			File file = resourceF.getFile();
			Scanner myReader = new Scanner(file);
		    while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        xml.append(data);
		    }
		    myReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return xml.toString();
    }
}
