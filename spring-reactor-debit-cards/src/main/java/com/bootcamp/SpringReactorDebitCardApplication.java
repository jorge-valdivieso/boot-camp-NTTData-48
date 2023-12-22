package com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringReactorDebitCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorDebitCardApplication.class, args);
	}

}
