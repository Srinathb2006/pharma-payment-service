package com.pharmacare.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentResponse {

    private String razorpayOrderId;
    private Double amount;
    public String getRazorpayOrderId() {
		return razorpayOrderId;
	}
	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	private String currency;
}
