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
import com.example.demo.api.v1.assembler.UsuarioModelAssembler;
import com.example.demo.api.v1.model.UsuarioModel;
import com.example.demo.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private GftLinks gftLinks;

	@Override
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
				.toCollectionModel(restaurante.getResponsaveis()).removeLinks()
				.add(gftLinks.linkToResponsaveisRestaurante(restauranteId))
				.add(gftLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(gftLinks.linkToRestauranteResponsavelDesassociacao(restauranteId, usuarioModel.getId(),
					"desassociar"));
		});

		return usuariosModel;
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();

	}

	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);

		return ResponseEntity.noContent().build();
	}
}
