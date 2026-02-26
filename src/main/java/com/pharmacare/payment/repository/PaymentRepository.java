package com.pharmacare.payment.repository;

import com.pharmacare.payment.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}
