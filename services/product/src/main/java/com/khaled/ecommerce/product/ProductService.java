package com.khaled.ecommerce.product;

import java.util.stream.Collectors;
import java.util.*;

import com.khaled.ecommerce.execption.ProductPurchaseExecption;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseExecption("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailable_quantity() < productRequest.quantity()) {
                throw new ProductPurchaseExecption("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailable_quantity() - productRequest.quantity();
            product.setAvailable_quantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
    public ProductResponse findById(Integer productid) {
        return repository.findById(productid)
                .map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Not found with the id "+ productid));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
