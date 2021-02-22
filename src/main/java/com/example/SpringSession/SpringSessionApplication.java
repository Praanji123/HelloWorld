package com.example.SpringSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class SpringSessionApplication {

	public static void main(String[] args) {

		System.out.println("Hello World");
		SpringApplication.run(SpringSessionApplication.class, args);

	}

}
