package com.pharmacare.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "inventory-service",
        url = "${inventory.service.url}"
)
public interface InventoryClient {

    @PutMapping("/api/purchase-orders/{id}/mark-paid")
    void markPurchaseOrderPaid(@PathVariable("id") String id);
}