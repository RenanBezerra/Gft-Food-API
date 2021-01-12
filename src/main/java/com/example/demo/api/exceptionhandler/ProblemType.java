package com.example.demo.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos","Dados inválidos"),
	ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro invalido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
	RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrada", "Recurso não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ACESSO_NEGADO("acesso-negado", "Acesso negado");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://GftFood.com.br" + path;
		this.title = title;
	}

}
