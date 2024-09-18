package com.khaled.ecommerce.order;

import com.khaled.ecommerce.customer.CustomerClient;
import com.khaled.ecommerce.exectptions.BusinessExecption;
import com.khaled.ecommerce.kafka.OrderConfirmation;
import com.khaled.ecommerce.kafka.OrderProducer;
import com.khaled.ecommerce.orderLine.OrderLineRequest;
import com.khaled.ecommerce.orderLine.OrderLineService;
import com.khaled.ecommerce.payment.PaymentClient;
import com.khaled.ecommerce.payment.PaymentRequest;
import com.khaled.ecommerce.prodcut.ProdcutClient;
import com.khaled.ecommerce.prodcut.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final PaymentClient paymentClient;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessExecption("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = prodcutClient.purchaseProducts(request.products());

        var order = this.orderRepository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrederConfirmation(
                new OrderConfirmation(
                        order.getReference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

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
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order with id " + orderId));
    }
}
