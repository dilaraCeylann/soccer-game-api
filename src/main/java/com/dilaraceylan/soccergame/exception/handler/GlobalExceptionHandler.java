package com.dilaraceylan.soccergame.exception.handler;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dilaraceylan.soccergame.http.concrete.BadRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadRequest validationError(ConstraintViolationException ex) {
//        BindingResult result = ex.getBindingResult();
//        final List<FieldError> fieldErrors = result.getFieldErrors();
//        fieldErrors.toArray(new FieldError[fieldErrors.size()]);
//
//        StringBuilder builder = new StringBuilder();
//        for (FieldError error : result.getFieldErrors()) {
//            builder.append(error.getDefaultMessage());
//        }
        return  new BadRequest("123123123");
    }
}