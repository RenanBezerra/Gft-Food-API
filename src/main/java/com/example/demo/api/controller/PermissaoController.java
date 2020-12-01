package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.PermissaoModelAssembler;
import com.example.demo.api.model.PermissaoModel;
import com.example.demo.api.openapi.controller.PermissaoControllerOpenApi;
import com.example.demo.domain.model.Permissao;
import com.example.demo.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Override
	public CollectionModel<PermissaoModel> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
		return permissaoModelAssembler.toCollectionModel(todasPermissoes);
	}

}
