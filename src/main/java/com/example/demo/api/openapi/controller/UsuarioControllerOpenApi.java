package com.example.demo.api.openapi.controller;

import java.util.List;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.UsuarioModel;
import com.example.demo.api.model.input.SenhaInput;
import com.example.demo.api.model.input.UsuarioComSenhaInput;
import com.example.demo.api.model.input.UsuarioInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuarios")
	List<UsuarioModel> listar();

	@ApiOperation("Busca um usúario por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID do usúario inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Usúario não encontrado", response = Problem.class)

	})
	UsuarioModel buscar(@ApiParam(value = "ID do usuario", example = "1", required = true) Long usuarioId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({ @ApiResponse(code = 201, message = "Usúario cadastrado") })
	UsuarioModel adicionar(
			@ApiParam(name = "corpo", value = "corpo", required = true) UsuarioComSenhaInput usuarioInput);

	@ApiOperation("Atualiza um usúario por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Usúario atualizado"),
			@ApiResponse(code = 404, message = "Usúario não encontrado", response = Problem.class)

	})
	UsuarioModel atualizar(@ApiParam(value = "ID do usuario", example = "1", required = true) Long usuarioId,
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioInput usuarioInput);

	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({ @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 404, message = "Usúario não encontrado", response = Problem.class)

	})
	void alterarSenha(@ApiParam(value = "ID do usuario", example = "1", required = true) Long usuarioId,
			@ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaInput senha);
}
