package com.khaled.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService  service;
    @PostMapping
    public ResponseEntity createProduct(
            @RequestBody @Valid ProductRequest request

    ) {
       return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> request
    ){
        return ResponseEntity.ok(service.purchaseProducts(request));
    }
    @GetMapping("/{productid}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("productid") Integer productid
    )
    {
        return ResponseEntity.ok(service.findById(productid));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
