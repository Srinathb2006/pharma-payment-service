package com.pharmacare.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients

@SpringBootApplication
public class PharmaPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaPaymentServiceApplication.class, args);
	}

}
