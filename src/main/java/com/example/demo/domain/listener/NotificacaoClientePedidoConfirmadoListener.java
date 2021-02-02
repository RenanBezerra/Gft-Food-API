package com.example.demo.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.demo.domain.event.PedidoConfirmadoEvent;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.service.EnvioEmailService;
import com.example.demo.domain.service.EnvioEmailService.Mensagem;

import lombok.var;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail;

	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();

		var mensagem = Mensagem.builder().assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html").variavel("pedido", pedido).destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
}
