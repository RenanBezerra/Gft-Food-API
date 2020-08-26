package com.example.demo.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.FotoProduto;

@Repository
public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
}
