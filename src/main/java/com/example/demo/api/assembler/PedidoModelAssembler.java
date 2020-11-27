package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.PedidoController;
import com.example.demo.api.model.PedidoModel;
import com.example.demo.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(gftLinks.linkToPedidos("pedidos"));
		
		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(gftLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			
		}
		if (pedido.podeSerCancelado()) {
			pedidoModel.add(gftLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			
		}

		if (pedido.podeSerEntregue()) {
			pedidoModel.add(gftLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			
		}

		pedidoModel.getRestaurante().add(gftLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoModel.getCliente().add(gftLinks.linkToUsuario(pedido.getCliente().getId()));

		pedidoModel.getFormaPagamento().add(gftLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

		pedidoModel.getEnderecoEntrega().getCidade()
				.add(gftLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

		pedidoModel.getItens().forEach(item -> {
			item.add(gftLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});

		return pedidoModel;

	}

}
