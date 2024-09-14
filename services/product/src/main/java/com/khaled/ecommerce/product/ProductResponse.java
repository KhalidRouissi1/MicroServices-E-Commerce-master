package com.khaled.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


public record ProductResponse(
        Integer id,
        String name,
        String description,
        double availabelQuantity,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}
