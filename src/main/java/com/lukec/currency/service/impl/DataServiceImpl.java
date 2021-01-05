package com.lukec.currency.service.impl;

import com.lukec.currency.bo.CurrencySubmission;
import com.lukec.currency.bo.SavedCurrencySubmission;
import com.lukec.currency.entity.ConversionHistory;
import com.lukec.currency.exception.ServiceException;
import com.lukec.currency.exception.ServiceException.ServiceExceptionCode;
import com.lukec.currency.jpa.repository.ConversionHistoryRepository;
import com.lukec.currency.service.DataService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
	private Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
	private final ConversionHistoryRepository repository;
	
	public DataServiceImpl(final ConversionHistoryRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ConversionHistory saveSubmission(SavedCurrencySubmission sent) {
		if(sent.getCurrency() == null) {
			throw new ServiceException(ServiceExceptionCode.VALIDATION_ERROR, "SRC: Missing Source Currency");
		}
		ConversionHistory history = new ConversionHistory(sent);
		logger.warn("Source currency is "+history.getSourceCurrency()+" from "+sent.getSourceCurrency());
		if(history.getSourceCurrency() == null) {
			throw new ServiceException(ServiceExceptionCode.VALIDATION_ERROR, "Missing Source Currency");
		}
		if(history.getDestinationCurrency() == null) {
			throw new ServiceException(ServiceExceptionCode.VALIDATION_ERROR, "Missing Destination Currency");
		}
		if(history.getSourceAmount() == null) {
			throw new ServiceException(ServiceExceptionCode.VALIDATION_ERROR, "Missing Source Amount");
		}
		if(history.getCalculatedAmount() == null) {
			throw new ServiceException(ServiceExceptionCode.VALIDATION_ERROR, "Missing Dest Amount");
		}
		try {
			this.repository.save(history);
		}
		catch(Exception e) {
			throw new ServiceException(ServiceExceptionCode.INTERNAL_ERROR, e.getMessage());
		}
		return history;
	}

	@Override
	public List<CurrencySubmission> retrieve() {
		List<ConversionHistory> list = repository.findAll();
		List<CurrencySubmission> listReturn = new ArrayList<>();
		for(ConversionHistory conv: list) {
			CurrencySubmission submission = new CurrencySubmission(conv);
			listReturn.add(submission);
		}
		return listReturn;
	}

}
