package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.RestauranteController;
import com.example.demo.api.v1.model.RestauranteBasicoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}

	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		RestauranteBasicoModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		if (gftSecurity.podeConsultarRestaurantes()) {

			restauranteModel.add(gftLinks.linkToRestaurantes("restaurantes"));
		}

		if (gftSecurity.podeConsultarCozinhas()) {

			restauranteModel.getCozinha().add(gftLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteBasicoModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(gftLinks.linkToRestaurantes());
		}

		return collectionModel;
	}

}
