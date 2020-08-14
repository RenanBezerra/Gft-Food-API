package com.example.demo.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.PedidoModelAssembler;
import com.example.demo.api.assembler.PedidoResumoModelAssembler;
import com.example.demo.api.disassembler.PedidoInputDisassembler;
import com.example.demo.api.model.PedidoModel;
import com.example.demo.api.model.PedidoResumoModel;
import com.example.demo.api.model.input.PedidoInput;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.model.Usuario;
import com.example.demo.domain.repository.PedidoRepository;
import com.example.demo.domain.repository.filter.PedidoFilter;
import com.example.demo.domain.service.EmissaoPedidoService;
import com.example.demo.infrastructure.repository.spec.PedidoSpecs;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;


	
	@GetMapping
	public List<PedidoResumoModel> pesquisar(PedidoFilter filtro) {
		List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));

		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {

			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedidoService.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
