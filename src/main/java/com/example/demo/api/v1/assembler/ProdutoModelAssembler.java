package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.RestauranteController;
import com.example.demo.api.v1.model.ProdutoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;
	
	@Autowired
	private GftSecurity gftSecurity;

	public ProdutoModelAssembler() {
		super(RestauranteController.class, ProdutoModel.class);
	}

	@Override
	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

		modelMapper.map(produto, produtoModel);

		if (gftSecurity.podeConsultarRestaurantes()) {
			
		produtoModel.add(gftLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

		produtoModel.add(gftLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));

		}
		return produtoModel;
	}

}
