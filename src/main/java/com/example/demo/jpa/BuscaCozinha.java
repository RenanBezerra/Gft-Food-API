package com.example.demo.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.example.demo.GftFoodApiApplication;
import com.example.demo.domain.model.Cozinha;

public class BuscaCozinha {

	public static void main(String[] args) {
		ApplicationContext aplicationContext = new SpringApplicationBuilder(GftFoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CadastroCozinha cadastroCozinha = aplicationContext.getBean(CadastroCozinha.class);

		Cozinha cozinha = cadastroCozinha.buscar(1l);

		System.out.println(cozinha.getNome());

	}
}
