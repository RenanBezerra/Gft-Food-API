package com.example.demo.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.api.model.EnderecoModel;
import com.example.demo.domain.model.Endereco;

import lombok.var;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
//				RestauranteModel::setPrecoFrete);

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		return modelMapper;
	}

}
