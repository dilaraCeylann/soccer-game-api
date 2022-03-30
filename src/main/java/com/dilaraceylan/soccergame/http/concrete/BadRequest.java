package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class BadRequest extends AbstractActionResult {

    public BadRequest() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequest(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequest(Object data) {
        super(HttpStatus.BAD_REQUEST, data);
    }

    public BadRequest(String message,Object data) {
        super(HttpStatus.BAD_REQUEST, message,data);
    }

}
