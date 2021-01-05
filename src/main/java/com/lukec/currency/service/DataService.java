package com.lukec.currency.service;

import java.util.List;

import com.lukec.currency.bo.CurrencySubmission;
import com.lukec.currency.bo.SavedCurrencySubmission;
import com.lukec.currency.entity.ConversionHistory;

public interface DataService {
	ConversionHistory saveSubmission(SavedCurrencySubmission sent);

	List<CurrencySubmission> retrieve();
}
