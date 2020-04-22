package com.example.demo.domain.repository;

import java.util.List;

import com.example.demo.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> listar();

	Estado buscar(Long id);

	Estado salvar(Estado estado);

	void remover(Estado estado);
}
