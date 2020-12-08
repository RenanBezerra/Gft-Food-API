package com.example.demo.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.example.demo.api.v1.model.CidadeModel;

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
