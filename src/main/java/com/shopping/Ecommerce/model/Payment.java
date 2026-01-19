package com.shopping.Ecommerce.model;


import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentGateway; // MOCK / RAZORPAY
    private String transactionId;
    private String razorpayOrderId; // Added field
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment() {}

    public Payment(Order order, String paymentGateway, String transactionId, PaymentStatus status) {
        this.order = order;
        this.paymentGateway = paymentGateway;
        this.transactionId = transactionId;
        this.status = status;
    }
    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getPaymentGateway() { return paymentGateway; }
    public void setPaymentGateway(String paymentGateway) { this.paymentGateway = paymentGateway; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}
