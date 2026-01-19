package com.shopping.Ecommerce.controller;

import com.shopping.Ecommerce.dto.PaymentRequest;
import com.shopping.Ecommerce.model.Payment;
import com.shopping.Ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Create a payment for an order (MOCK)
     */
    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest request) {
        return paymentService.createPayment(request.getOrderId());
    }
}
