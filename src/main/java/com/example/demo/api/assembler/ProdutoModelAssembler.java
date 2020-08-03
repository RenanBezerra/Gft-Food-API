package com.example.demo.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.model.ProdutoModel;
import com.example.demo.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}

	public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
		return produtos.stream().map(produto -> toModel(produto)).collect(Collectors.toList());
	}

}
