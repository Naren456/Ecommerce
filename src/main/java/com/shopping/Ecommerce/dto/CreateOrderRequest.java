package com.shopping.Ecommerce.dto;



public class CreateOrderRequest {

    private Long userId;

    public CreateOrderRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
