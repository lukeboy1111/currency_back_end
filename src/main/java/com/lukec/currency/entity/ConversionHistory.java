package com.lukec.currency.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lukec.currency.bo.SavedCurrencySubmission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "conversion_history")
public class ConversionHistory {
	public ConversionHistory(SavedCurrencySubmission sent) {
		this.sourceCurrency = sent.getSourceCurrency();
		this.destinationCurrency = sent.getCurrency();
		this.sourceAmount = sent.getAmount();
		this.calculatedAmount = sent.getCalculatedAmount();
		this.rateSource = sent.getSource();
		this.notes = sent.getComment();
		this.dateOfEntry = new Date();
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private BigInteger id;

	@Column(name = "source_currency")
	private String sourceCurrency;
	
	@Column(name = "destination_currency")
	private String destinationCurrency;
	
	@Column(name = "source_amount")
	private BigDecimal sourceAmount;
	
	@Column(name = "calculated_amount")
	private BigDecimal calculatedAmount;
	
	@Column(name = "rate_source")
	private String rateSource;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "date_entry")
	private Date dateOfEntry;
}
