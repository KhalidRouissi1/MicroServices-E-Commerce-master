package com.khaled.ecommerce.exectptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessExecption extends RuntimeException {
    private final String msg;
}
