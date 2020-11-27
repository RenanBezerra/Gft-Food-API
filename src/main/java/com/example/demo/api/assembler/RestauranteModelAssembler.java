package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.RestauranteController;
import com.example.demo.api.model.RestauranteModel;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		restauranteModel.add(gftLinks.linkToRestaurantes("restaurantes"));

		restauranteModel.getCozinha().add(gftLinks.linkToCozinha(restaurante.getCozinha().getId()));

		restauranteModel.getEndereco().getCidade()
				.add(gftLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		restauranteModel.add(gftLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));

		restauranteModel.add(gftLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(gftLinks.linkToRestauranteInativacao(restaurante.getId(), "ativar"));
		}
		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(gftLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}
		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(gftLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}
		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(gftLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {

		return super.toCollectionModel(entities).add(gftLinks.linkToRestaurantes());
	}
}
