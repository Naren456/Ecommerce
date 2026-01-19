package com.shopping.Ecommerce.webhook;

import com.shopping.Ecommerce.dto.PaymentWebhookRequest;
import com.shopping.Ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Mock payment gateway webhook
     * Called when payment is completed
     */
    @PostMapping
    public String handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {

        if ("SUCCESS".equalsIgnoreCase(request.getStatus())) {
            paymentService.markPaymentSuccess(request.getPaymentId());
            return "Payment SUCCESS webhook processed";
        }

        return "Webhook received but payment not successful";
    }
}
