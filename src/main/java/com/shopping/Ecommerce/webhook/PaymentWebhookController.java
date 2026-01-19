// src/main/java/com/shopping/Ecommerce/webhook/PaymentWebhookController.java
package com.shopping.Ecommerce.webhook;

import com.razorpay.Utils;
import com.shopping.Ecommerce.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    @Value("${razorpay.webhook-secret}")
    private String webhookSecret;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public String handlePaymentWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature) {

        try {
            // Verify signature
            boolean isValid = Utils.verifyWebhookSignature(payload, signature, webhookSecret);
            if (!isValid) return "Invalid Signature";

            JSONObject json = new JSONObject(payload);
            String event = json.getString("event");

            if ("payment.captured".equals(event)) {
                String razorpayOrderId = json.getJSONObject("payload")
                        .getJSONObject("payment")
                        .getJSONObject("entity")
                        .getString("order_id");

                paymentService.markPaymentSuccess(razorpayOrderId);
                return "Processed Successfully";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return "Event Ignored";
    }
}