package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class IntervalServerError extends AbstractActionResult {

    public IntervalServerError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public IntervalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public IntervalServerError(Object data) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

}
