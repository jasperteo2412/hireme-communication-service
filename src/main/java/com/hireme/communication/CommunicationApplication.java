package com.hireme.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {
		"com.hireme.communication"
})
public class CommunicationApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "OPTIONS", "UPDATE", "DELETE").allowedOrigins("*")
				.allowedHeaders("*");
	}
}
