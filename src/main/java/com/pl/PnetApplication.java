package com.pl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class PnetApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PnetApplication.class, args);
	}
}
