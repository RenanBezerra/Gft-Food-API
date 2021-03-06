package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.RestauranteProdutoFotoController;
import com.example.demo.api.v1.model.FotoProdutoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
	}

	@Override
	public FotoProdutoModel toModel(FotoProduto foto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);

		if (gftSecurity.podeConsultarRestaurantes()) {

			fotoProdutoModel.add(gftLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));

			fotoProdutoModel.add(gftLinks.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));

		}

		return fotoProdutoModel;
	}

}
