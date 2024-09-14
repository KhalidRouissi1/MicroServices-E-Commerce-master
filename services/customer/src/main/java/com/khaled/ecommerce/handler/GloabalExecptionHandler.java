package com.khaled.ecommerce.handler;

import com.khaled.ecommerce.exception.CustomerNotFoundExecption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GloabalExecptionHandler {

    @ExceptionHandler(CustomerNotFoundExecption.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundExecption e) {
        return ResponseEntity
                .status(
                        HttpStatus.NOT_FOUND
                ).body(
                        e.getMsg()
                );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(MethodArgumentNotValidException e) {
        var errors= new HashMap<String,String >();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError)error).getField();
            var errorMsg = error.getDefaultMessage();
            errors.put(fieldName, errorMsg);
        });
        return ResponseEntity
                .status(
                        HttpStatus.BAD_REQUEST
                ).body(
                        new ErrorResponse(errors)
                );
    }

}
