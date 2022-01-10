package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MicroservicesApplication {
//https://spring.io/guides/gs/accessing-data-mysql/
	public static void main(String[] args) {
		SpringApplication.run(MicroservicesApplication.class, args);
	}

}
