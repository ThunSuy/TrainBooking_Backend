package com.doan.trainbooker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.doan.trainbooker" })
public class TrainBookerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainBookerApplication.class, args);
	}
	
}
