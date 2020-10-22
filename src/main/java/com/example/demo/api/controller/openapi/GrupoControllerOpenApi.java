package com.example.demo.api.controller.openapi;

import java.util.List;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.GrupoModel;
import com.example.demo.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	public List<GrupoModel> listar();

	@ApiOperation("Busca um grupo po ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado"), })
	public GrupoModel adicionar(GrupoInput grupoInput);

	@ApiOperation("Atualiza um grupo po ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public GrupoModel atualizar(Long grupoId, GrupoInput grupoInput);

	@ApiOperation("Exclui um grupo po ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Grupo excluido"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	public void remover(Long grupoId);

}
