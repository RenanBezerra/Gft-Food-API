package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoRepository pedidoRepository;
//	@Autowired
//	private EnvioEmailService envioEmail;
//	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
		
//		var mensagem = Mensagem.builder()
//				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
//				.corpo("pedido-confirmado.html")
//				.variavel("pedido", pedido)
//				.destinatario(pedido.getCliente().getEmail())
//				.build();
//		
//		envioEmail.enviar(mensagem);
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}

}