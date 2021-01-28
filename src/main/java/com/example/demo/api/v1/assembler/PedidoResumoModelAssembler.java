package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.PedidoController;
import com.example.demo.api.v1.model.PedidoResumoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		if (gftSecurity.podePesquisarPedidos()) {
			
			pedidoModel.add(gftLinks.linkToPedidos("pedidos"));

		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			pedidoModel.getRestaurante().add(gftLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}

		if (gftSecurity.podeConsultarUsuariosGruposPermissoes()) {

			pedidoModel.getCliente().add(gftLinks.linkToUsuario(pedido.getCliente().getId()));
		}

		return pedidoModel;

	}
}
