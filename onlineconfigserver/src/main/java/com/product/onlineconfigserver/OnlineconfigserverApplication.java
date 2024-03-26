package com.product.onlineconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OnlineconfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineconfigserverApplication.class, args);
	}

}
