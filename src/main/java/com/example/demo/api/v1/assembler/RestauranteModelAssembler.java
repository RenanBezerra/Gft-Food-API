package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.RestauranteController;
import com.example.demo.api.v1.model.RestauranteModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteModel);

		if (gftSecurity.podeConsultarRestaurantes()) {

			restauranteModel.add(gftLinks.linkToRestaurantes("restaurantes"));

		}
		if (gftSecurity.podeGerenciarCadastroRestaurantes()) {

			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(gftLinks.linkToRestauranteInativacao(restaurante.getId(), "ativar"));
			}
			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(gftLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}
		if (gftSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {

			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(gftLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}
			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(gftLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			restauranteModel.add(gftLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}

		if (gftSecurity.podeConsultarCozinhas()) {

			restauranteModel.getCozinha().add(gftLinks.linkToCozinha(restaurante.getCozinha().getId()));

		}
		if (gftSecurity.podeConsultarCidades()) {

			if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {

				restauranteModel.getEndereco().getCidade()
						.add(gftLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}

		if (gftSecurity.podeConsultarRestaurantes()) {

			restauranteModel.add(gftLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}
		if (gftSecurity.podeGerenciarCadastroRestaurantes()) {

			restauranteModel.add(gftLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
		}
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(gftLinks.linkToRestaurantes());
		}
		return collectionModel;
	}
}
