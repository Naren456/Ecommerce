package com.shopping.Ecommerce.dto;

public class PaymentRequest {

    private Long orderId;

    public PaymentRequest() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
}

