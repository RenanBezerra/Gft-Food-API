package com.example.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.GrupoModelAssembler;
import com.example.demo.api.model.GrupoModel;
import com.example.demo.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.example.demo.domain.model.Usuario;
import com.example.demo.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Override
	@GetMapping
	public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

		return grupoModelAssembler.toCollectionModel(usuario.getGrupos()).removeLinks();
	}

	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}

	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
	}

}
