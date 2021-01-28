package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.GrupoController;
import com.example.demo.api.v1.model.GrupoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private GftSecurity gftSecurity;

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	@Override
	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);

		if (gftSecurity.podeConsultarUsuariosGruposPermissoes()) {

			grupoModel.add(gftLinks.linkToGrupos("grupos"));

			grupoModel.add(gftLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}

		return grupoModel;
	}

	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {

		CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarUsuariosGruposPermissoes()) {

			collectionModel.add(gftLinks.linkToGrupos());
		}
		return collectionModel;
	}

}
