package com.example.demo.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlandia")
	private String nome;

	private EstadoModel estado;

}
