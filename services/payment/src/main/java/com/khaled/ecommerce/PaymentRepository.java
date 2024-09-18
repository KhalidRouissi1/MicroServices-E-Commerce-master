package com.khaled.ecommerce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
