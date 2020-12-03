package com.example.demo.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.example.demo.api.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadeModel")
@Data
public class CidadesModelOpenApi {

	private CidadeEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi {

		private List<CidadeModel> cidades;
	}
}
