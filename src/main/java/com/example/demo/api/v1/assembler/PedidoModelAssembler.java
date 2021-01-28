package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.PedidoController;
import com.example.demo.api.v1.model.PedidoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		if (gftSecurity.podePesquisarPedidos()) {

			pedidoModel.add(gftLinks.linkToPedidos("pedidos"));
		}

		if (gftSecurity.podeGerenciarPedidos(pedido.getCodigo())) {

			if (pedido.podeSerConfirmado()) {
				pedidoModel.add(gftLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));

			}
			if (pedido.podeSerCancelado()) {
				pedidoModel.add(gftLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));

			}

			if (pedido.podeSerEntregue()) {
				pedidoModel.add(gftLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));

			}
		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			pedidoModel.getRestaurante().add(gftLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}

		if (gftSecurity.podeConsultarUsuariosGruposPermissoes()) {

			pedidoModel.getCliente().add(gftLinks.linkToUsuario(pedido.getCliente().getId()));
		}

		if (gftSecurity.podeConsultarFormasPagamento()) {

			pedidoModel.getFormaPagamento().add(gftLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		}

		if (gftSecurity.podeConsultarCidades()) {

			pedidoModel.getEnderecoEntrega().getCidade()
					.add(gftLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			pedidoModel.getItens().forEach(item -> {
				item.add(gftLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
			});
		}

		return pedidoModel;

	}
}
