package com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringReactorBankTransfersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorBankTransfersApplication.class, args);
	}

}
