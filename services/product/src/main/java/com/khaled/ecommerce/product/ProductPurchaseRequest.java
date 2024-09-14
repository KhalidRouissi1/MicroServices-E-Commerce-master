package com.khaled.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;


public record ProductPurchaseRequest(
        @NotNull(message = "Product is mandatory") Integer productId,
        @NotNull(message = "Quantity is mandatory") double quantity

) {}
