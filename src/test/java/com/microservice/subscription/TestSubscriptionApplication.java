package com.microservice.subscription;

import org.springframework.boot.SpringApplication;

public class TestSubscriptionApplication {

	public static void main(String[] args) {
		SpringApplication.from(SubscriptionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
