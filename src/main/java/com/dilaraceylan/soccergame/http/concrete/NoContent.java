package com.dilaraceylan.soccergame.http.concrete;

import org.springframework.http.HttpStatus;

import com.dilaraceylan.soccergame.http.abstracts.AbstractActionResult;

public class NoContent extends AbstractActionResult {

    public NoContent() {
        super(HttpStatus.NO_CONTENT);
    }

    public NoContent(String message) {
        super(HttpStatus.NO_CONTENT, message);
    }

    public NoContent(Object data) {
        super(HttpStatus.NO_CONTENT, data);
    }

    public NoContent(String message,Object data) {
        super(HttpStatus.NO_CONTENT, message,data);
    }

}
