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
import com.example.demo.api.v1.assembler.PermissaoModelAssembler;
import com.example.demo.api.v1.model.PermissaoModel;
import com.example.demo.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.example.demo.domain.model.Grupo;
import com.example.demo.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private GftLinks gftLinks;

	@Override
	@GetMapping
	public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

		CollectionModel<PermissaoModel> permissoesModel = permissaoModelAssembler
				.toCollectionModel(grupo.getPermissoes()).removeLinks().add(gftLinks.linkToGrupoPermissoes(grupoId))
				.add(gftLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

		permissoesModel.getContent().forEach(permissaoModel -> {
			permissoesModel
					.add(gftLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar"));
		});

		return permissoesModel;

	}

	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
}
