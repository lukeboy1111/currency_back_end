package com.lukec.currency.bo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversion {
	private String id;
	private String currency;
	private BigDecimal rate;
}
