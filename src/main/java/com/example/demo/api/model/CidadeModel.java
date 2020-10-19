package com.example.demo.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlandia")
	private String nome;
	
	private EstadoModel estado;
	
}
