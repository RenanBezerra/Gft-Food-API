package com.example.demo.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.api.v1.model.EnderecoModel;
import com.example.demo.api.v1.model.input.ItemPedidoInput;
import com.example.demo.api.v2.model.input.CidadeInputV2;
import com.example.demo.domain.model.Cidade;
import com.example.demo.domain.model.Endereco;
import com.example.demo.domain.model.ItemPedido;

import lombok.var;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
			.addMappings(mapper -> mapper.skip(Cidade::setId));
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete,
//				RestauranteModel::setPrecoFrete);

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		return modelMapper;
	}

}
