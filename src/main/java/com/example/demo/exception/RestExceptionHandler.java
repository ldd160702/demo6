package com.example.demo.exception;

import com.example.demo.model.MyErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<MyErrorResponse> handleException(UserNotFoundException exception) {
        MyErrorResponse myErrorResponse = new MyErrorResponse();
        myErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        myErrorResponse.setMessage(exception.getMessage());
        myErrorResponse.setTimestamp(new Date());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MyErrorResponse> handleException(Exception exception) {
        MyErrorResponse myErrorResponse = new MyErrorResponse();
        myErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        myErrorResponse.setMessage(exception.getMessage());
        myErrorResponse.setTimestamp(new Date());
        return new ResponseEntity<>(myErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
