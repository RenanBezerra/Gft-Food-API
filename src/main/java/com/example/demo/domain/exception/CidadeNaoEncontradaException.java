package com.example.demo.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
	}
}
