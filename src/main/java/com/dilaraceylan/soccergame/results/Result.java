package com.dilaraceylan.soccergame.results;

import lombok.ToString;

@ToString
public class Result implements IResult {

    private boolean success;

    private String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success) {
        this.success = success;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String message() {
        return message;
    }

	@Override
	public void setMessage(String message) {
		this.message = 	message;
	}

}
