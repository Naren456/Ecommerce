package com.shopping.Ecommerce.service;

import com.shopping.Ecommerce.model.Order;
import com.shopping.Ecommerce.model.OrderStatus;
import com.shopping.Ecommerce.model.Payment;
import com.shopping.Ecommerce.model.PaymentStatus;
import com.shopping.Ecommerce.repository.OrderRepository;
import com.shopping.Ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentGateway("MOCK");
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(PaymentStatus.CREATED);

        return paymentRepository.save(payment);
    }

    public void markPaymentSuccess(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }
}
