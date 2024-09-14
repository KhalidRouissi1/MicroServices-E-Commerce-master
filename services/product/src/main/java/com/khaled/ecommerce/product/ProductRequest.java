package com.khaled.ecommerce.product;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
public record ProductRequest(

           Integer id,
         @NotNull(message = "prodcut name is required")
         String name,
         @NotNull(message = "description name is required")
         String description,
         @NotNull(message = "available quantitiy should be positve")
         double availabelQuantity,
         @NotNull(message = "price should be positve")
         BigDecimal price,
         @NotNull(message = "poduct category is required")
         Integer categoryId
) {
}
