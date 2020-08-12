package com.example.demo.api.model;

import com.example.demo.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
}
