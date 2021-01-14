package com.example.demo.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.assembler.FormaPagamentoModelAssembler;
import com.example.demo.api.v1.model.FormaPagamentoModel;
import com.example.demo.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.example.demo.core.security.CheckSecurity;
import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@Autowired
	private GftLinks gftLinks;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler
				.toCollectionModel(restaurante.getFormasPagamento()).removeLinks()
				.add(gftLinks.linkToRestauranteFormasPagamento(restauranteId))
				.add(gftLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

		formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
			formasPagamentoModel.add(gftLinks.linkToRestauranteFormaPagamentoDesassociacao(restauranteId,
					formaPagamentoModel.getId(), "desassociar"));
		});

		return formasPagamentoModel;

	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@DeleteMapping("{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@PutMapping("{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

		return ResponseEntity.noContent().build();
	}
}
