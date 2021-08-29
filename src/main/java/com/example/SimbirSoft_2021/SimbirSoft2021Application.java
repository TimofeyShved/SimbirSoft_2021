package com.example.SimbirSoft_2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimbirSoft2021Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SimbirSoft2021Application.class, args);
	}

}
