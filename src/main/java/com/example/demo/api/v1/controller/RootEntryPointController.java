package com.example.demo.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.core.security.GftSecurity;

import lombok.var;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private GftLinks gftLink;

	@Autowired
	private GftSecurity gftSecurity;

	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();

		if (gftSecurity.podeConsultarCozinhas()) {

			rootEntryPointModel.add(gftLink.linkToCozinhas("cozinhas"));
		}

		if (gftSecurity.podePesquisarPedidos()) {

			rootEntryPointModel.add(gftLink.linkToPedidos("pedidos"));
		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			rootEntryPointModel.add(gftLink.linkToRestaurantes("restaurantes"));
		}

		if (gftSecurity.podeConsultarUsuariosGruposPermissoes()) {

			rootEntryPointModel.add(gftLink.linkToGrupos("grupos"));
			rootEntryPointModel.add(gftLink.linkToUsuarios("usuarios"));
			rootEntryPointModel.add(gftLink.linkToPermissoes("permissoes"));
		}

		if (gftSecurity.podeConsultarFormasPagamento()) {

			rootEntryPointModel.add(gftLink.linkToFormasPagamento("formas-pagamento"));
		}

		if (gftSecurity.podeConsultarEstados()) {

			rootEntryPointModel.add(gftLink.linkToEstados("estados"));
		}

		if (gftSecurity.podeConsultarCidades()) {

			rootEntryPointModel.add(gftLink.linkToCidades("cidades"));
		}

		if (gftSecurity.podeConsultarEstatisticas()) {

			rootEntryPointModel.add(gftLink.linkToEstatisticas("estatisticas"));
		}

		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

	}

}
