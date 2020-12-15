package com.example.demo.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.example.demo.api.v2.assembler.CozinhaModelAssemblerV2;
import com.example.demo.api.v2.model.CozinhaModelV2;
import com.example.demo.api.v2.model.input.CozinhaInputV2;
import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.repository.CozinhaRepository;
import com.example.demo.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaModelAssemblerV2 cozinhaModelAssemblerV2;

	@Autowired
	private CozinhaInputDisassemblerV2 cozinhaInputDisassemblerV2;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping
	public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		PagedModel<CozinhaModelV2> cozinhaPagedModel = pagedResourcesAssembler.toModel(cozinhasPage,
				cozinhaModelAssemblerV2);

		return cozinhaPagedModel;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModelV2 buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		return cozinhaModelAssemblerV2.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInputV2) {
		Cozinha cozinha = cozinhaInputDisassemblerV2.toDomainObject(cozinhaInputV2);
		cozinha = cadastroCozinhaService.salvar(cozinha);

		return cozinhaModelAssemblerV2.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModelV2 atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputV2 cozinhaInputV2) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassemblerV2.copyToDomainObject(cozinhaInputV2, cozinhaAtual);
		cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);

		return cozinhaModelAssemblerV2.toModel(cozinhaAtual);
	}

}
