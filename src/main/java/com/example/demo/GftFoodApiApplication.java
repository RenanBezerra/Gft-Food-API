package com.example.demo;

import com.example.demo.core.io.Base64ProtocolResolver;
import com.example.demo.infrastructure.repository.CustomJpaRepositoryImpl;

import lombok.var;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;




@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class GftFoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		var app = new SpringApplication(GftFoodApiApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
		
		//SpringApplication.run(GftFoodApiApplication.class, args);
	}

}
