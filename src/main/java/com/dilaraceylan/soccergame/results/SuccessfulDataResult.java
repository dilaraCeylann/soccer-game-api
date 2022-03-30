package com.dilaraceylan.soccergame.results;

public class SuccessfulDataResult<T> extends DataResult<T> {

    public SuccessfulDataResult() {
        super(null, true);
    }

    public SuccessfulDataResult(String message) {
        super(null, true, message);
    }

    public SuccessfulDataResult(T data, String message) {
        super(data, true, message);
    }

    public SuccessfulDataResult(T data) {
        super(data, true);
    }
}