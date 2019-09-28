package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class BackendSocketApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendSocketApplication.class, args);
	}

}
