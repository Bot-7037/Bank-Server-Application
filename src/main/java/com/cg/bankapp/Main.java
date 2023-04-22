package com.cg.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Driver class to start the application
 * 
 * @author himanegi
 *
 */
@SpringBootApplication
@EnableSwagger2
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
