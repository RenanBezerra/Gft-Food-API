package com.example.demo.api.v2.controller.openApi;

import org.springframework.hateoas.CollectionModel;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.v2.model.CidadeModelV2;
import com.example.demo.api.v2.model.input.CidadeInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModelV2> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "ID da cidade inválido", response = Problem.class) })
	CidadeModelV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
	CidadeModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 CidadeInputV2);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	CidadeModelV2 atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true) CidadeInputV2 CidadeInputV2);

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cidade excluida"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}
