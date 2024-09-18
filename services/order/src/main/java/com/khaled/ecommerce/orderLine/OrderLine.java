package com.khaled.ecommerce.orderLine;

import com.khaled.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customer_line")
public class OrderLine {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST) // Add cascade option
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}

