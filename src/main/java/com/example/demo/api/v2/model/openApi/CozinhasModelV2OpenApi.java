package com.example.demo.api.v2.model.openApi;

import java.util.List;

import org.springframework.hateoas.Links;

import com.example.demo.api.v1.model.CozinhaModel;
import com.example.demo.api.v1.openapi.model.CozinhasModelOpenApi.CozinhasEmbeddedModelOpenApi;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelV2OpenApi {

	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelV2OpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhaEmbeddedModelOpenApi{
		
		private List<CozinhaModel> cozinhas;
	}
}
