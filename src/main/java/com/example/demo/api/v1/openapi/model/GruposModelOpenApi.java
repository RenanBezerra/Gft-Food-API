package com.example.demo.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.example.demo.api.v1.model.GrupoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

	private GruposEmbeddedodelOpenApi _embedded;
	private Links _links;

	@ApiModel("GruposEmbeddedModel")
	@Data
	public class GruposEmbeddedodelOpenApi {

		private List<GrupoModel> grupos;
	}
}
