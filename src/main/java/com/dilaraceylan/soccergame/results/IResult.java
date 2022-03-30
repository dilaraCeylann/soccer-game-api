package com.dilaraceylan.soccergame.results;

import java.io.Serializable;

public interface IResult extends Serializable {

    boolean success();

    String message();
    
    void setMessage(String message);

}
