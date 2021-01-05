package com.lukec.currency.controller;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.service.CurrentRatesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/currency")
@Api("Currency View API")
public class ApiCurrencyController {
	private final CurrentRatesService currentRatesService;
	
	public ApiCurrencyController(final CurrentRatesService currentRatesService) {
		this.currentRatesService = currentRatesService;
	}
	
	@GetMapping(value = "/today", produces = "application/json")
    @ApiOperation(value = "Returns today's feeds")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", response = CurrencyConversionEntry.class)}
    )
	public CurrencyConversionEntry currentRates() {
		try {
			return currentRatesService.euroFxDaily();
		}
		catch(DocumentRetrievalException e) {
			return new CurrencyConversionEntry();
		}
	}
	
	@GetMapping(value = "/history", produces = "application/json")
    @ApiOperation(value = "Returns today's feeds")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", responseContainer = "List", response = CurrencyConversionEntry.class)}
    )
	public List<CurrencyConversionEntry> historyRates() {
		try {
			return currentRatesService.euroFxHistory();
		}
		catch(DocumentRetrievalException e) {
			return null;
		}
	}
	
}
