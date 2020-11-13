package com.example.turni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TurniApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurniApplication.class, args);
	}

}
