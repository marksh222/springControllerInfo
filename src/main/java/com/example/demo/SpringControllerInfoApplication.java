package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class SpringControllerInfoApplication {

	@RequestMapping
	public static void main(String[] args) {
		SpringApplication.run(SpringControllerInfoApplication.class, args);
	}

}
