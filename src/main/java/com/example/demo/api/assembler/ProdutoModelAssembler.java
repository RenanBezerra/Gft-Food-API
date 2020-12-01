package com.example.demo.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.GftLinks;
import com.example.demo.api.controller.RestauranteController;
import com.example.demo.api.model.ProdutoModel;
import com.example.demo.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftLinks gftLinks;

	public ProdutoModelAssembler() {
		super(RestauranteController.class, ProdutoModel.class);
	}

	@Override
	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

		modelMapper.map(produto, produtoModel);

		produtoModel.add(gftLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

		produtoModel.add(gftLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));

		return produtoModel;
	}

}
