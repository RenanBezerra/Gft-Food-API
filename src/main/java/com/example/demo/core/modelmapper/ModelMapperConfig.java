package com.example.demo.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.api.model.RestauranteModel;
import com.example.demo.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
				RestauranteModel::setPrecoFrete);

		return modelMapper;
	}

}
