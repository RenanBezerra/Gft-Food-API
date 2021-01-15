package com.example.demo.api.v1.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v1.assembler.PedidoModelAssembler;
import com.example.demo.api.v1.assembler.PedidoResumoModelAssembler;
import com.example.demo.api.v1.disassembler.PedidoInputDisassembler;
import com.example.demo.api.v1.model.PedidoModel;
import com.example.demo.api.v1.model.PedidoResumoModel;
import com.example.demo.api.v1.model.input.PedidoInput;
import com.example.demo.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.example.demo.core.data.PageWrapper;
import com.example.demo.core.data.PageableTranslator;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.filter.PedidoFilter;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.model.Usuario;
import com.example.demo.domain.repository.PedidoRepository;
import com.example.demo.domain.service.EmissaoPedidoService;
import com.example.demo.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

import lombok.var;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

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

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

	@Autowired
	private GftSecurity gftSecurity;
	
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}

	@Override
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {

			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(gftSecurity.getUsuarioId());

			novoPedido = emissaoPedidoService.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);

		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable apiPageable) {

		var mapeamento = ImmutableMap.of("codigo", "codigo", "restaurante.nome", "restaurante.nome", "nomeCliente",
				"cliente.nome", "valorTotal", "valorTotal");
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
