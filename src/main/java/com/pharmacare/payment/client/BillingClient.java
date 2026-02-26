package com.pharmacare.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.pharmacare.payment.config.FeignClientInterceptor;

@FeignClient(
        name = "billing-service",
        url = "${billing.service.url}",
        configuration = FeignClientInterceptor.class
)
public interface BillingClient {

    @PutMapping("/api/billing/{id}/mark-paid")
    void markBillPaid(@PathVariable("id") String id);
}