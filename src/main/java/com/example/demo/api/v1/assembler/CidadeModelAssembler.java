package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.CidadeController;
import com.example.demo.api.v1.model.CidadeModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Override
	public CidadeModel toModel(Cidade cidade) {

		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeModel);

		if (gftSecurity.podeConsultarCidades()) {

			cidadeModel.getEstado().add(gftLinks.linkToCidades("cidades"));
		}

		if (gftSecurity.podeConsultarEstados()) {

			cidadeModel.getEstado().add(gftLinks.linkToEstado(cidadeModel.getEstado().getId()));
		}

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {

		CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarCidades()) {

			collectionModel.add(gftLinks.linkToCidades());
		}
		return collectionModel;
	}

//	public List<CidadeModelV2> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//	}

}
