package com.lukec.currency.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.lukec.currency.bo.CurrencyConversion;
import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.repository.EcbRetrievalRepository;
import com.lukec.currency.service.impl.CurrentRatesServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestCurrentRatesService {
	
	@InjectMocks
	@Spy
	private CurrentRatesServiceImpl service;
	
	@Mock
	private EcbRetrievalRepository repository;
	
	private CurrencyConversionEntry listCurrencies;
	
	@Mock
	private CurrencyConversion conversion;
	
	private List<CurrencyConversionEntry> listConversions;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(conversion.getCurrency()).thenReturn("GBP");
		Mockito.when(conversion.getRate()).thenReturn(BigDecimal.ONE);
		List<CurrencyConversion> list = new ArrayList<>();
		list.add(conversion);
		listCurrencies = new CurrencyConversionEntry("2020-10-31", list);
		
		listConversions = new ArrayList<>();
		listConversions.add(listCurrencies);
		
	}
	
	@Test
	public void testToday() throws DocumentRetrievalException {
		Mockito.when(repository.euroFxDaily()).thenReturn(listCurrencies);
		CurrencyConversionEntry c = service.euroFxDaily();
		assertThat(c).isNotNull();
		assertThat(c.getListCurrencies()).isNotNull();
		assertThat(c.getListCurrencies().size()).isGreaterThan(0);
		CurrencyConversion co = c.getListCurrencies().get(0);
		assertThat(co).isNotNull();
		assertThat(co.getCurrency()).isEqualTo("GBP");
		assertThat(co.getRate().toPlainString()).isEqualTo("1");
	}
	
	@Test(expected = DocumentRetrievalException.class)
	public void testExceptionThrown() throws DocumentRetrievalException {
		Mockito.when(repository.euroFxDaily()).thenThrow(DocumentRetrievalException.class);
		CurrencyConversionEntry c = service.euroFxDaily();
	}
	
	@Test
	public void testHistory() throws DocumentRetrievalException {
		Mockito.when(repository.euroFxHistory()).thenReturn(listConversions);
		List<CurrencyConversionEntry> l = service.euroFxHistory();
		assertThat(l).isNotNull();
		assertThat(l.size()).isEqualTo(1);
		CurrencyConversionEntry c = l.get(0);
		assertThat(c).isNotNull();
		assertThat(c.getListCurrencies()).isNotNull();
		assertThat(c.getListCurrencies().size()).isGreaterThan(0);
		CurrencyConversion co = c.getListCurrencies().get(0);
		assertThat(co).isNotNull();
		assertThat(co.getCurrency()).isEqualTo("GBP");
		assertThat(co.getRate().toPlainString()).isEqualTo("1");
	}
	
	@Test(expected = DocumentRetrievalException.class)
	public void testExceptionThrownHistory() throws DocumentRetrievalException {
		Mockito.when(repository.euroFxHistory()).thenThrow(DocumentRetrievalException.class);
		List<CurrencyConversionEntry> c = service.euroFxHistory();
	}
	
}
