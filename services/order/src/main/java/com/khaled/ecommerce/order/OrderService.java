package com.khaled.ecommerce.order;

import com.khaled.ecommerce.customer.CustomerClient;
import com.khaled.ecommerce.exectptions.BusinessExecption;
import com.khaled.ecommerce.kakfa.OrderConfirmaiotn;
import com.khaled.ecommerce.kakfa.OrderProducer;
import com.khaled.ecommerce.orderLine.OrderLineRequest;
import com.khaled.ecommerce.orderLine.OrderLineService;
import com.khaled.ecommerce.prodcut.ProdcutClient;
import com.khaled.ecommerce.prodcut.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProdcutClient prodcutClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createOrder(OrderRequest request){
        //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()->new BusinessExecption("Cannot create order, No customer found with the provided id"+request.customerId()));
        //purchase the products --> product-ms (Rest Tempalte)
        var purchasedProducts = this.prodcutClient.purchaseProducts(request.prodcuts());
        var order = this.orderRepository.save(mapper.toOrder(request));
        //persist order         //persist the order line
        for (PurchaseRequest purchaseRequest: request.prodcuts()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        //todo start payment process
        orderProducer.sendOrederConfirmation(
                new OrderConfirmaiotn(
                        request.refrence(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        //send the order confirmation --> send notification-ms (kafka)
        return order.getId();
    };

    public List<OrderResponse> findAll() {
            return orderRepository
                    .findAll()
                    .stream()
                    .map(mapper::fromOrder)
                    .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException("Cannot find order with id "+orderId));
    }
}
