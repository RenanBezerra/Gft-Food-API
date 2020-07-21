package com.example.demo.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.model.input.CozinhaInput;
import com.example.demo.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);

	}

	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);

	}

}
