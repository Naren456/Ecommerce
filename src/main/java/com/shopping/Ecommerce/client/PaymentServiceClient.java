package com.shopping.Ecommerce.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import java.util.HashMap;

@Component
public class PaymentServiceClient {

    private final RestTemplate restTemplate;

    @Value("${payment.service.url:http://localhost:8081}")
    private String paymentServiceUrl;

    public PaymentServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> createMockPayment(Long orderId, Double amount) {
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", orderId);
        request.put("amount", amount);

        try {
            return restTemplate.postForObject(paymentServiceUrl + "/payments/create", request, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Mock Payment Service: " + e.getMessage());
        }
    }
}
