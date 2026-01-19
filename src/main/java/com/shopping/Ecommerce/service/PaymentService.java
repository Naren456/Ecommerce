// src/main/java/com/shopping/Ecommerce/service/PaymentService.java
package com.shopping.Ecommerce.service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shopping.Ecommerce.model.*;
import com.shopping.Ecommerce.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RazorpayClient razorpayClient;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository,
                          @Value("${razorpay.key-id}") String keyId,
                          @Value("${razorpay.key-secret}") String keySecret) throws RazorpayException {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.razorpayClient = new RazorpayClient(keyId, keySecret);
    }

    public Payment createPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (order.getTotalAmount() * 100)); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + orderId);

            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentGateway("RAZORPAY");
            payment.setRazorpayOrderId(razorpayOrder.get("id"));
            payment.setStatus(PaymentStatus.CREATED);

            return paymentRepository.save(payment);
        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay failure: " + e.getMessage());
        }
    }

    public void markPaymentSuccess(String razorpayOrderId) {
        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }
}