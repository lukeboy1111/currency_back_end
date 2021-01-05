package com.lukec.currency.service;

import java.util.List;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;

public interface CurrentRatesService {
	CurrencyConversionEntry euroFxDaily() throws DocumentRetrievalException;

	List<CurrencyConversionEntry> euroFxHistory() throws DocumentRetrievalException;
	
}
