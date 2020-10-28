package com.example.demo.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlandia")
	private String nome;

	@ApiModelProperty(example = "Minas Gerais")
	private String estado;

}
