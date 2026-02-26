package com.pharmacare.payment.service;

import com.pharmacare.payment.dto.*;
import com.pharmacare.payment.entity.Payment;

import java.util.List;

public interface PaymentService {

    CreatePaymentResponse createOrder(CreatePaymentRequest request);

    void verifyPayment(VerifyPaymentRequest request);

    List<Payment> getAllPayments();          

    Payment getPaymentById(String id);      
}
