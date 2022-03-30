package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class ForbiddenRequest extends AbstractActionResult {

    public ForbiddenRequest() {
        super(HttpStatus.FORBIDDEN);
    }

    public ForbiddenRequest(Object data) {
        super(HttpStatus.FORBIDDEN, data);
    }

    public ForbiddenRequest(String message, Object data) {
        super(HttpStatus.FORBIDDEN, message, data);
    }

}
