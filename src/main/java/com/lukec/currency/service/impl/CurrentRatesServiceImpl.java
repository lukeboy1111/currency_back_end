package com.lukec.currency.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.repository.EcbRetrievalRepository;
import com.lukec.currency.service.CurrentRatesService;

@Service
public class CurrentRatesServiceImpl implements CurrentRatesService {
	private EcbRetrievalRepository repository;
	
	public CurrentRatesServiceImpl(EcbRetrievalRepository repository) {
		this.repository = repository;
	}

	@Override
	public CurrencyConversionEntry euroFxDaily() throws DocumentRetrievalException {
		return repository.euroFxDaily();
	}

	@Override
	public List<CurrencyConversionEntry> euroFxHistory() throws DocumentRetrievalException {
		return repository.euroFxHistory();
	}
	
	
}
