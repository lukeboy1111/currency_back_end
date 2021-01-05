package com.lukec.currency.bo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedCurrencySubmission {
	@JsonProperty(value="currency")
	private String currency;
	@JsonProperty(value="sourceCurrency")
	private String sourceCurrency;
	@JsonProperty(value="rate")
	private BigDecimal rate;
	@JsonProperty(value="amount")
	private BigDecimal amount;
	@JsonProperty(value="calculatedAmount")
    private BigDecimal calculatedAmount;
	@JsonProperty(value="source")
    private String source;
	@JsonProperty(value="comment")
    private String comment;
	
	
}
