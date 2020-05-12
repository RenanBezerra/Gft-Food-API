package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.domain.model.Cozinha;

@Component
public interface CozinhaRepository {

	List<Cozinha> listar();
	
	List<Cozinha> consultaPorNome(String nome);

	Cozinha buscar(Long id);

	Cozinha salvar(Cozinha cozinha);

	void remover(Long id);
}
