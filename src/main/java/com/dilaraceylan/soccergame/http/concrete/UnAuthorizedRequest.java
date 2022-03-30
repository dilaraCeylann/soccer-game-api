package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class UnAuthorizedRequest extends AbstractActionResult {

    public UnAuthorizedRequest(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnAuthorizedRequest() {
        super(HttpStatus.UNAUTHORIZED);
    }

}
