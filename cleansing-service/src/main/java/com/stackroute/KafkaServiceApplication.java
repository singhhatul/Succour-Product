package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class KafkaServiceApplication {
	public static void main(String[] args) throws Exception{
		SpringApplication.run(KafkaServiceApplication.class, args);
	}

}
