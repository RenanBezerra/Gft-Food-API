 package com.example.demo.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.model.input.RestauranteInput;
import com.example.demo.domain.model.Cidade;
import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		//Para evitar org.hibernate.HibernateException: identifier of an instance of
		// com.example.GftFood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
}