package com.example.demo.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.repository.CozinhaRepository;
import com.example.demo.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(String nome){
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists (String nome){
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(
			String nome,Long cozinhaId){
		
		return restauranteRepository.consultaPorNome(nome, cozinhaId);
	}
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantesPorTaxaFrete(
			String nome){
		
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	@GetMapping("/restaurantes/dois-por-nome")
	public List<Restaurante> restaurantesPortop2(
			String nome){
		
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(
			Long cozinhaId){
		
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(
			String nome,BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	
	
	
}
 