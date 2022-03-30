package com.dilaraceylan.soccergame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2
@EnableSwagger2WebMvc
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
	}
}
