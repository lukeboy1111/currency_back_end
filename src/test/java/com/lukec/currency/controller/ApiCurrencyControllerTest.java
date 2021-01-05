package com.lukec.currency.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukec.currency.bo.CurrencyConversion;
import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.config.WebConfig;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.service.CurrentRatesService;
import com.lukec.currency.util.TestUrlUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class, WebConfig.class })
public class ApiCurrencyControllerTest {
	private Logger logger = LoggerFactory.getLogger(ApiCurrencyControllerTest.class);

	private final ObjectMapper mapper = new ObjectMapper();

	@MockBean
	private CurrentRatesService ratesService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private CurrencyConversionEntry listCurrencies;

	private CurrencyConversion conversion;

	private List<CurrencyConversionEntry> listConversion;

	@Before
	public void setUp() throws DocumentRetrievalException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		MockitoAnnotations.initMocks(this);

		conversion = new CurrencyConversion("GBP","GBP", BigDecimal.ONE);
		List<CurrencyConversion> list = new ArrayList();
		list.add(conversion);
		listCurrencies = new CurrencyConversionEntry("2020-12-31", list);
		Mockito.when(ratesService.euroFxDaily()).thenReturn(listCurrencies);
		listConversion = new ArrayList<>();
		listConversion.add(listCurrencies);
		Mockito.when(ratesService.euroFxHistory()).thenReturn(listConversion);
	}

	@Test
	public void testCurrentGivenTheUrlItReturnsItemsOfSize1() throws Exception {
		String testUrl = TestUrlUtil.getControllerTestUrl(format("/v1/currency/today/"));
		logger.warn("URl=" + testUrl);

		final MockHttpServletResponse response = mockMvc.perform(get(testUrl)).andReturn().getResponse();
		// Then -- I should get a valid response
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		// Then -- The item should convert into an object
		final CurrencyConversionEntry body = mapper.readValue(response.getContentAsString(),
				new TypeReference<CurrencyConversionEntry>() {
				});
		assertThat(body).isNotNull();
		assertThat(body.getListCurrencies()).isNotNull();
		assertThat(body.getListCurrencies().size()).isEqualTo(1);

	}

	@Test
	public void testCurrentGivenTheUrlItDiesWith401() throws Exception {
		Mockito.when(ratesService.euroFxDaily()).thenThrow(DocumentRetrievalException.class);
		String testUrl = TestUrlUtil.getControllerTestUrl(format("/v1/currency/today/"));
		logger.warn("URl=" + testUrl);

		final MockHttpServletResponse response = mockMvc.perform(get(testUrl)).andReturn().getResponse();
		// Then -- I should get a valid response
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		// Then -- The item should convert into an object
		final CurrencyConversionEntry body = mapper.readValue(response.getContentAsString(),
				new TypeReference<CurrencyConversionEntry>() {
				});
		assertThat(body).isNotNull();
		assertThat(body.getListCurrencies()).isNull();

	}

	@Test
	public void testHistoryGivenTheUrlItReturnsItemsOfSize1() throws Exception {
		String testUrl = TestUrlUtil.getControllerTestUrl(format("/v1/currency/history/"));
		logger.warn("URl=" + testUrl);

		final MockHttpServletResponse response = mockMvc.perform(get(testUrl)).andReturn().getResponse();
		// Then -- I should get a valid response
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		// Then -- The item should convert into an object
		final List<CurrencyConversionEntry> body = mapper.readValue(response.getContentAsString(),
				new TypeReference<List<CurrencyConversionEntry>>() {
				});
		assertThat(body).isNotNull();
		assertThat(body.size()).isEqualTo(1);
		CurrencyConversionEntry c = body.get(0);
		assertThat(c).isNotNull();
		assertThat(c.getListCurrencies()).isNotNull();
		assertThat(c.getListCurrencies().size()).isEqualTo(1);
	}

}
