package com.khaled.ecommerce.order;

import com.khaled.ecommerce.prodcut.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String refrence,
        @Positive(message = "Order amount should be positive ")
        BigDecimal amount,
        @NotNull(message =  "Payment method should not be precised")
        PaymentMethod paymentMethod,
        @NotNull(message =  "Customer should be present")
        @NotEmpty(message =  "Customer should be present")
        @NotBlank(message =  "Customer should be present")

        String customerId,
        @NotNull(message = "You should at least purchase a product")
        List<PurchaseRequest> prodcuts
) {
}
