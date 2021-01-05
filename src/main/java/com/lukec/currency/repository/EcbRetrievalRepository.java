package com.lukec.currency.repository;

import java.util.List;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;

public interface EcbRetrievalRepository {

	CurrencyConversionEntry euroFxDaily() throws DocumentRetrievalException;
	List<CurrencyConversionEntry> euroFxHistory() throws DocumentRetrievalException;
}
