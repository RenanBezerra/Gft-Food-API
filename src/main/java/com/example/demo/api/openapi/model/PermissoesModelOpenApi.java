package com.example.demo.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.example.demo.api.model.PermissaoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissoesModel")
@Data
public class PermissoesModelOpenApi {

	private PermissoesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("PermissoesEmbeddedModel")
	@Data
	public class PermissoesEmbeddedModelOpenApi {

		private List<PermissaoModel> permissoes;
	}
}
