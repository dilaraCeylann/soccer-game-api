package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class Found extends AbstractActionResult {

    public Found() {
        super(HttpStatus.FOUND);
    }

    public Found(Object data) {
        super(HttpStatus.FOUND, data);
    }

    public Found(String message) {
        super(HttpStatus.FOUND, message);
    }

    public Found(String message, Object data) {
        super(HttpStatus.FOUND, message, data);
    }

}
