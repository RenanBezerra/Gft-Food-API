package com.example.demo.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.FotoProdutoModel;
import com.example.demo.api.model.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

	@ApiOperation(value = "Busca a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Produto de restaurante não encontrada", response = Problem.class)

	})
	FotoProdutoModel buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

	@ApiOperation("Atualiza a foto do produto de um restaurante")
	@ApiResponses({ @ApiResponse(code = 200, message = "Foto do produto atualizada"),
			@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)

	})
	FotoProdutoModel atualizarFoto(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,

			FotoProdutoInput fotoProdutoInput,
			@ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true) MultipartFile arquivo)
			throws IOException;

	@ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

	@ApiOperation("Exclui a foto do produto de um restaurante")
	@ApiResponses({ @ApiResponse(code = 204, message = "Foto do produto excluida"),
			@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Foto do produto não encontrada", response = Problem.class)

	})
	void excluir(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,

			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);
}
