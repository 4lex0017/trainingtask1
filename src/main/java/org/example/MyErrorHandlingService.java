package org.example;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class MyErrorHandlingService {

    @ExceptionHandler(EntityNotFoundException.class)
    protected String handleExcepiton(EntityNotFoundException exception) {
        System.out.println("runtime exception lul");
        return "Exception";
    }
}
