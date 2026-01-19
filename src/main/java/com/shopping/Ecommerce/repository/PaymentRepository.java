// src/main/java/com/shopping/Ecommerce/repository/PaymentRepository.java
package com.shopping.Ecommerce.repository;

import com.shopping.Ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}