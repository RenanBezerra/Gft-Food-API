package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.UsuarioController;
import com.example.demo.api.model.UsuarioModel;
import com.example.demo.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	public UsuarioModel toModel(Usuario usuario) {

		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);

		modelMapper.map(usuario, usuarioModel);

		usuarioModel.add(gftLinks.linkToUsuarios("usuarios"));

		usuarioModel.add(gftLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities).add(gftLinks.linkToUsuarios());
	}

}
