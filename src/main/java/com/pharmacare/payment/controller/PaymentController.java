package com.pharmacare.payment.controller;

import com.pharmacare.payment.dto.*;
import com.pharmacare.payment.entity.Payment;
import com.pharmacare.payment.service.PaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    
    @PostMapping("/create-order")
    public CreatePaymentResponse createOrder(@RequestBody CreatePaymentRequest request) {
        return service.createOrder(request);
    }

    

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody VerifyPaymentRequest request) {
        service.verifyPayment(request);
    }


    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable String id) {
        return service.getPaymentById(id);
    }
}
