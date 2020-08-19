package com.example.demo.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.model.dto.VendaDiaria;
import com.example.demo.domain.service.VendaQueryService;

import lombok.var;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@Autowired
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);

		var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));

		var selection = builder.construct(VendaDiaria.class, functionDateDataCriacao, builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));

		query.select(selection);
		query.groupBy(functionDateDataCriacao);

		return manager.createQuery(query).getResultList();
	}

}
