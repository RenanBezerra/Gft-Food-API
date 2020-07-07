package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		// cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// acao
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

		// validacao
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test(expected = ConstraintViolationException.class)
	public void testarCadastroCozinhaSemNome() {
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		//acao
		novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
		
		
		//validacao
	}
}
