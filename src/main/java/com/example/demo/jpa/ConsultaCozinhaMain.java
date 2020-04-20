package com.example.demo.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.example.demo.GftFoodApiApplication;
import com.example.demo.domain.model.Cozinha;

public class ConsultaCozinhaMain {

	public static void main (String[] args) {
		ApplicationContext aplicationContext = new SpringApplicationBuilder(GftFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = aplicationContext.getBean(CadastroCozinha.class);
		
		List<Cozinha> cozinhas = cadastroCozinha.listar();
		
		for (Cozinha cozinha :cozinhas) {
			System.out.println(cozinha.getNome());
		}
				
	}
}
