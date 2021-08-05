package com.myproject.restaurantvoting.error.exceptions;

import com.myproject.restaurantvoting.error.AppException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class VotingTimeLimitException extends AppException {

    public VotingTimeLimitException(String msg) {
        super(HttpStatus.METHOD_NOT_ALLOWED, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}
