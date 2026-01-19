package com.shopping.Ecommerce.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${razorpay.key-id}")
    private String razorpayKeyId;

    @GetMapping("/razorpay")
    public Map<String, String> getRazorpayKey() {
        return Map.of("key", razorpayKeyId);
    }
}
