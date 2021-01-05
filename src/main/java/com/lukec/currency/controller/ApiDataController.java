package com.lukec.currency.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lukec.currency.bo.CurrencyConversionEntry;
import com.lukec.currency.bo.CurrencySubmission;
import com.lukec.currency.bo.SavedCurrencySubmission;
import com.lukec.currency.entity.ConversionHistory;
import com.lukec.currency.exception.DocumentRetrievalException;
import com.lukec.currency.service.DataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/data")
@Api("Save Data API")
public class ApiDataController {
	private final DataService dataService;
	
	public ApiDataController(final DataService dataService) {
		this.dataService = dataService;
	}
	
	@PostMapping(value = "/save", produces = "application/json")
    @ApiOperation(value = "Saves an item of type SavedCurrencySubmission")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful save", response = ConversionHistory.class),
            @ApiResponse(code = 401, message = "Incorrect submission"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
	public ConversionHistory saveSubmission(@RequestBody SavedCurrencySubmission submission) {
		return dataService.saveSubmission(submission);
	}
	
	@GetMapping(value = "/retrieve", produces = "application/json")
    @ApiOperation(value = "Returns saved items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", responseContainer = "List", response = CurrencySubmission.class)}
    )
	public List<CurrencySubmission> retrieve() {
		
		return dataService.retrieve();
		
	}
	
}
