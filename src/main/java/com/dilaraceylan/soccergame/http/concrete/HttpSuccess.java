package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class HttpSuccess extends AbstractActionResult {

    public HttpSuccess() {
        super(HttpStatus.OK);
    }

    public HttpSuccess(String message) {
        super(HttpStatus.OK, message);
    }

    public HttpSuccess(Object data) {
        super(HttpStatus.OK, data);
    }

    public HttpSuccess(String message, Object data) {
        super(HttpStatus.OK, message, data);
    }

}
