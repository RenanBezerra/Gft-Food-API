package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.CidadeController;
import com.example.demo.api.model.CidadeModel;
import com.example.demo.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Override
	public CidadeModel toModel(Cidade cidade) {

		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeModel);

		cidadeModel.getEstado().add(gftLinks.linkToCidades("cidades"));

		cidadeModel.getEstado().add(gftLinks.linkToEstado(cidadeModel.getEstado().getId()));

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(gftLinks.linkToCidades());
	}

//	public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//	}

}
