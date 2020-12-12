package com.example.demo.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v1.GftLinks;

import lombok.var;

@RestController
@RequestMapping(path = "/v1" ,produces= MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private GftLinks gftLink;

	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();

		rootEntryPointModel.add(gftLink.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(gftLink.linkToCozinhas("gastronomias"));
		rootEntryPointModel.add(gftLink.linkToPedidos("pedidos"));
		rootEntryPointModel.add(gftLink.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(gftLink.linkToGrupos("grupos"));
		rootEntryPointModel.add(gftLink.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(gftLink.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(gftLink.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(gftLink.linkToEstados("estados"));
		rootEntryPointModel.add(gftLink.linkToCidades("cidades"));
		rootEntryPointModel.add(gftLink.linkToEstatisticas("estatisticas"));

		return rootEntryPointModel;
	}

	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

	}

}
