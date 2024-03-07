package com.tiid.tienda.controllers;

import com.tiid.tienda.exceptions.GeneralExpection;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.exceptions.ShippingError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundExecption.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundExecption ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(ShippingError.class)
    public ResponseEntity<String> handleResourceShippingException(ShippingError ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(GeneralExpection.class)
    public ResponseEntity<String> handleResourceNotValidOrder(GeneralExpection ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
