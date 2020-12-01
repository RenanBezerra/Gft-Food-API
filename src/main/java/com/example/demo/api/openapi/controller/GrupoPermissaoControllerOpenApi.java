package com.example.demo.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões associadas a um grupo")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
	CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

	@ApiOperation("Desassociação de permissão com grupo")
	@ApiResponses({ @ApiResponse(code = 204, message = "Desassociação"),
			@ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class) })
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,

			@ApiParam(value = "ID do permissão", example = "1", required = true) Long permissaoId);

	@ApiOperation("Lista as permissões associadas a um grupo")
	@ApiResponses({ @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
			@ApiResponse(code = 404, message = "Grupo ou permmissão não encontrada", response = Problem.class) })
	ResponseEntity<Void> associar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,

			@ApiParam(value = "ID do permissão", example = "1", required = true) Long permissaoId);
}
