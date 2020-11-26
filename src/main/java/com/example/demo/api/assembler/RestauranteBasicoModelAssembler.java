package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.RestauranteController;
import com.example.demo.api.model.RestauranteBasicoModel;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}

	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		RestauranteBasicoModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		restauranteModel.add(gftLinks.linkToRestaurantes("restaurantes"));

		restauranteModel.getCozinha().add(gftLinks.linkToCozinha(restaurante.getCozinha().getId()));

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(gftLinks.linkToRestaurantes());
	}

}
