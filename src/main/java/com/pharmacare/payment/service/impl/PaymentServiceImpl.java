package com.pharmacare.payment.service.impl;

import com.pharmacare.payment.client.BillingClient;
import com.pharmacare.payment.client.InventoryClient;
import com.pharmacare.payment.dto.*;
import com.pharmacare.payment.entity.Payment;
import com.pharmacare.payment.repository.PaymentRepository;
import com.pharmacare.payment.service.PaymentService;
import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final RazorpayClient razorpayClient;
    private final BillingClient billingClient;
    private final InventoryClient inventoryClient;

    public PaymentServiceImpl(PaymentRepository repository,
                              RazorpayClient razorpayClient,
                              BillingClient billingClient,
                              InventoryClient inventoryClient) {
        this.repository = repository;
        this.razorpayClient = razorpayClient;
        this.billingClient = billingClient;
        this.inventoryClient = inventoryClient;
    }

    

    @Override
    public CreatePaymentResponse createOrder(CreatePaymentRequest request) {

        try {
           
            int amountInPaise = (int) Math.round(request.getAmount() * 100);

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);   
            options.put("currency", request.getCurrency());
            options.put("receipt", request.getReferenceId());

            Order order = razorpayClient.orders.create(options);

            Payment payment = new Payment();
            payment.setRazorpayOrderId(order.get("id").toString());
            payment.setReferenceId(request.getReferenceId());
            payment.setType(request.getType());
            payment.setAmount(request.getAmount());
            payment.setCurrency(request.getCurrency());
            payment.setStatus("CREATED");
            payment.setCreatedAt(LocalDateTime.now());

            repository.save(payment);

            CreatePaymentResponse response = new CreatePaymentResponse();
            response.setRazorpayOrderId(order.get("id").toString());
            response.setAmount(request.getAmount());
            response.setCurrency(request.getCurrency());

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error creating payment order: " + e.getMessage());
        }
    }
   

    @Override
    public void verifyPayment(VerifyPaymentRequest request) {

        Payment payment = repository.findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
        payment.setStatus("SUCCESS");

        repository.save(payment);

       
        if ("CUSTOMER".equalsIgnoreCase(payment.getType())) {
            billingClient.markBillPaid(payment.getReferenceId());
        }
        else if ("SUPPLIER".equalsIgnoreCase(payment.getType())) {
            inventoryClient.markPurchaseOrderPaid(payment.getReferenceId());
        }
    }

    

    @Override
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

   

    @Override
    public Payment getPaymentById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }
}
