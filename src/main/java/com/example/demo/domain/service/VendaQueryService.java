package com.example.demo.domain.service;

import java.util.List;

import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro,String timeOffset);

}
