package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.PedidoModelAssembler;
import com.example.demo.api.assembler.PedidoResumoModelAssembler;
import com.example.demo.api.model.PedidoModel;
import com.example.demo.api.model.PedidoResumoModel;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.repository.PedidoRepository;
import com.example.demo.domain.service.EmissaoPedidoService;

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

	@GetMapping
	public List<PedidoResumoModel> listar() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();

		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}

	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

		return pedidoModelAssembler.toModel(pedido);
	}
}
