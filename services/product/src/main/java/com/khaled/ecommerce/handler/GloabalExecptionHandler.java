package com.khaled.ecommerce.handler;

import com.khaled.ecommerce.execption.ProductPurchaseExecption;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GloabalExecptionHandler {

    @ExceptionHandler(ProductPurchaseExecption.class)
    public ResponseEntity<String> handleCustomerNotFound(ProductPurchaseExecption e) {
        return ResponseEntity
                .status(
                        HttpStatus.BAD_REQUEST
                ).body(
                        e.getMessage()
                );
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(ProductPurchaseExecption e) {
        return ResponseEntity
                .status(
                        HttpStatus.BAD_REQUEST
                ).body(
                        e.getMessage()
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
