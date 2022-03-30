package com.dilaraceylan.soccergame.results;

import lombok.ToString;

public class ErrorDataResult<T> extends DataResult<T> {

    public ErrorDataResult() {
        super(null, false);
    }

    public ErrorDataResult(String message) {
        super(null, false, message);
    }

    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResult(T data) {
        super(data, false);
    }
}
