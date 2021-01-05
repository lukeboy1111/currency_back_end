package com.lukec.currency.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApiException {
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private String message;

    private ApiException() {
        timestamp = new Date();
    }

    public ApiException(String message) {
        this();
        this.message = message;
    }
}
