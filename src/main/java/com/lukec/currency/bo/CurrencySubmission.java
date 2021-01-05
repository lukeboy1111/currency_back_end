package com.lukec.currency.bo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.lukec.currency.entity.ConversionHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CurrencySubmission extends SavedCurrencySubmission {
	public CurrencySubmission(ConversionHistory conv) {
		this.setAmount(conv.getSourceAmount());
		this.setCalculatedAmount(conv.getCalculatedAmount());
		this.setComment(conv.getNotes());
		this.setSourceCurrency(conv.getSourceCurrency());
		this.setCurrency(conv.getDestinationCurrency());
		//this.setId(conv.getId().toString());
		this.setSource(conv.getRateSource());
		this.setDateOfEntry(conv.getDateOfEntry());
	}

	private Date dateOfEntry;
}
