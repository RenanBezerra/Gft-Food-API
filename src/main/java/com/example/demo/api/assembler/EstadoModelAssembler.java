package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.EstadoController;
import com.example.demo.api.model.EstadoModel;
import com.example.demo.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public EstadoModel toModel(Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

		modelMapper.map(estado, estadoModel);

		estadoModel.add(gftLinks.linkToEstados("estados"));

		return estadoModel;
	}

	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities).add(gftLinks.linkToEstados());
	}
}
