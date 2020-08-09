package com.example.demo.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;

	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO));
		}

		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}

	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CANCELADO.getDescricao()));

		}

		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataCancelamento(OffsetDateTime.now());
	}

	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

		if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser aterado de %s para %s",
					pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
		}

		pedido.setStatus(StatusPedido.ENTREGUE);
		pedido.setDataEntrega(OffsetDateTime.now());
	}
}
