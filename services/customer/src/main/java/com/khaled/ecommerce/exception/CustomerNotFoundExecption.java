package com.khaled.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerNotFoundExecption extends RuntimeException {
    private final String msg;

}
