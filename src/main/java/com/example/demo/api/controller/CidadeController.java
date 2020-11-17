package com.example.demo.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.ResourceUriHelper;
import com.example.demo.api.assembler.CidadeModelAssembler;
import com.example.demo.api.disassembler.CidadeInputDisassembler;
import com.example.demo.api.model.CidadeModel;
import com.example.demo.api.model.input.CidadeInput;
import com.example.demo.api.openapi.controller.CidadeControllerOpenApi;
import com.example.demo.domain.exception.EstadoNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.model.Cidade;
import com.example.demo.domain.repository.CidadeRepository;
import com.example.demo.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@Autowired
	CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	CidadeInputDisassembler cidadeInputDisassembler;

	@Override
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		List<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(todasCidades);

		cidadesModel.forEach(cidadeModel -> {
			
			cidadeModel.getEstado().add(
					WebMvcLinkBuilder.linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel());
		
			cidadeModel.getEstado()
					.add(WebMvcLinkBuilder.linkTo(methodOn(EstadoController.class).listar()).withRel("cidades"));

			cidadeModel.getEstado().add(WebMvcLinkBuilder
					.linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId())).withSelfRel());

			
		});
		
		CollectionModel<CidadeModel> cidadeCollectionModel =new CollectionModel<>(cidadesModel);
	
		cidadeCollectionModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	
		return cidadeCollectionModel;
	
	}
	

	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

		cidadeModel.getEstado().add(
				WebMvcLinkBuilder.linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel());
	
		cidadeModel.getEstado()
				.add(WebMvcLinkBuilder.linkTo(methodOn(EstadoController.class).listar()).withRel("cidades"));

		cidadeModel.getEstado().add(WebMvcLinkBuilder
				.linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId())).withSelfRel());

		return cidadeModel;
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidadeService.salvar(cidade);

			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

			return cidadeModel;
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);

		}
	}

	@Override
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);

		}
	}

	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidadeService.excluir(cidadeId);

	}

}
