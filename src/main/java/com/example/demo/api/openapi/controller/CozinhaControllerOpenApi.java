package com.example.demo.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.CozinhaModel;
import com.example.demo.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	public Page<CozinhaModel> listar(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public CozinhaModel buscar(Long cozinhaId);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cozinha cadastrada") })
	public CozinhaModel adicionar(CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public CozinhaModel atualizar(Long cozinhaId, CozinhaInput cozinhaInput);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cozinha excluida"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) })
	public void remover(Long cozinhaId);
}
