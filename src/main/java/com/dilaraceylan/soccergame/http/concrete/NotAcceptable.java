package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class NotAcceptable extends AbstractActionResult {

    public NotAcceptable() {
        super(HttpStatus.NOT_ACCEPTABLE);
    }

    public NotAcceptable(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }

    public NotAcceptable(Object data) {
        super(HttpStatus.NOT_ACCEPTABLE, data);
    }

}
