package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.RestauranteController;
import com.example.demo.api.v1.model.RestauranteApenasNomeModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}

	@Override
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {

		RestauranteApenasNomeModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		if (gftSecurity.podeConsultarRestaurantes()) {

			restauranteModel.add(gftLinks.linkToRestaurantes("restaurantes"));

		}
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {

		CollectionModel<RestauranteApenasNomeModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarRestaurantes()) {

			collectionModel.add(gftLinks.linkToRestaurantes());

		}

		return collectionModel;
	}
}
