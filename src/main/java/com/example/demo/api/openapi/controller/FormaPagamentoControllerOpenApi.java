package com.example.demo.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.FormaPagamentoModel;
import com.example.demo.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento")
	public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) })
	public ResponseEntity<FormaPagamentoModel> buscar(Long formaPagamentoId, ServletWebRequest request);

	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({ @ApiResponse(code = 201, message = "Forma de pagamento cadastrada") })
	public FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) })
	public FormaPagamentoModel atualizar(Long formaPagamentoId, FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class) })
	public void remover(Long formaPagamentoId);
}
