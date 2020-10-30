package com.example.demo.api.openapi.controller;

import java.util.List;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.RestauranteModel;
import com.example.demo.api.model.input.RestauranteInput;
import com.example.demo.api.model.view.RestauranteView;
import com.example.demo.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projeção", paramType = "query", type = "string") })
	@JsonView(RestauranteView.Resumo.class)
	public List<RestauranteModel> listar();

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	public List<RestauranteModel> listarApenasNomes();

	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({

			@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public RestauranteModel buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({ @ApiResponse(code = 201, message = "Restaurante cadastrado") })
	public RestauranteModel adicionar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) RestauranteInput restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public RestauranteModel atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteInput restauranteInput);

	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativa um restarante ppor ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso") })
	public void ativarMultiplos(@ApiParam(value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurante ativados com sucesso") })
	public void inativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Restaurantes fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiResponses({ @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class) })
	public void fechar(Long restauranteId);

}
