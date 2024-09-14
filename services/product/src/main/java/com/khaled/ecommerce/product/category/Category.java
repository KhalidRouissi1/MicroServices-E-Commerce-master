package com.khaled.ecommerce.product.category;

import com.khaled.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "category"
    )
    private List<Product> products;
}
