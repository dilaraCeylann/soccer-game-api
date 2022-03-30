package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class NotFound extends AbstractActionResult {

    public NotFound() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFound(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFound(Object data) {
        super(HttpStatus.NOT_FOUND, data);
    }

    public NotFound(String message,Object data) {
        super(HttpStatus.NOT_FOUND, message,data);
    }

}
