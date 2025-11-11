package com.deliverytech.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DeliveryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApiApplication.class, args);
    }
}