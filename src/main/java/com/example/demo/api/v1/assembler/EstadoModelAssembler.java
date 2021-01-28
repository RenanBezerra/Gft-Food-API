package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.EstadoController;
import com.example.demo.api.v1.model.EstadoModel;
import com.example.demo.core.security.GftSecurity;
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

	@Autowired
	private GftSecurity gftSecurity;

	public EstadoModel toModel(Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

		modelMapper.map(estado, estadoModel);

		if (gftSecurity.podeConsultarEstados()) {

			estadoModel.add(gftLinks.linkToEstados("estados"));
		}

		return estadoModel;
	}

	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarEstados()) {

			collectionModel.add(gftLinks.linkToEstados());
		}

		return collectionModel;
	}
}
