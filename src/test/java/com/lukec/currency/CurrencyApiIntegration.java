package com.lukec.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.util.TestUrlUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyApiIntegration {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void shouldGetListOfItemsForCurrencyToday() {

		// Arrange

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUrlUtil.getIntegrationTestUrl("/v1/currency/today/", port));

		// Act

		ResponseEntity<CurrencyConversionEntry> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				null, new ParameterizedTypeReference<CurrencyConversionEntry>() {
				});

		// Assert

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertNotNull(response.getBody());

		CurrencyConversionEntry body = response.getBody();
		assertThat(body).isNotNull();
		assertThat(body.getListCurrencies()).isNotNull();
		assertThat(body.getListCurrencies().size()).isEqualTo(32);
	}
	
	
	@Test
	public void shouldGetListOfItemsForCurrencyHistory() {

		// Arrange

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(TestUrlUtil.getIntegrationTestUrl("/v1/currency/history/", port));

		// Act

		ResponseEntity<List<CurrencyConversionEntry>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<CurrencyConversionEntry>>() {
				});

		// Assert

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertNotNull(response.getBody());
		
		List<CurrencyConversionEntry> body = response.getBody();
		assertThat(body).isNotNull();
		assertThat(body.size()).isGreaterThan(1);
		CurrencyConversionEntry item = body.get(0);
		assertThat(item.getListCurrencies()).isNotNull();
		assertThat(item.getListCurrencies().size()).isEqualTo(32);
	}

}
