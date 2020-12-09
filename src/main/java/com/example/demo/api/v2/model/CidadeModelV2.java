package com.example.demo.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@ApiModelProperty(example = "1")
	private Long idCidade;

	@ApiModelProperty(example = "Uberlandia")
	private String nomeCidade;

	private Long idEstado;
	
	private String nomeEstado;

}
