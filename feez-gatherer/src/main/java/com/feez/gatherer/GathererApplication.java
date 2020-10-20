package com.feez.gatherer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class GathererApplication {

	public static void main(String[] args) {
		SpringApplication.run(GathererApplication.class, args);
	}

}
