package com.dilaraceylan.soccergame.http.abstracts;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;
/**
 * @author dilara.ceylan
 */
@ToString
public abstract class AbstractActionResult implements IActionResult {

    private String message;

    private Object data;

    @JsonIgnore
    private HttpStatus httpStatus;

    public AbstractActionResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        setStatus(httpStatus);
    }

    public AbstractActionResult(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus);
        this.message = message;
        this.data = data;
    }

    public AbstractActionResult(HttpStatus httpStatus, Object data) {
        this(httpStatus);
        this.data = data;
    }

    public AbstractActionResult(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public String getMessage() {
        return Objects.nonNull(message) ? message : getHttpStatus().getReasonPhrase();
    }

    public Object getData() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setStatus(HttpStatus httpStatus) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getResponse();
        response.setStatus(httpStatus.value());
    }

}
