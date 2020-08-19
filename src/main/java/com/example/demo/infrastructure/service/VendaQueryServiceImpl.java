package com.example.demo.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.Pedido;
import com.example.demo.domain.model.StatusPedido;
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
		
		var predicates = new ArrayList<Predicate>();
		
		var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));

		var selection = builder.construct(VendaDiaria.class, functionDateDataCriacao, builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		if (filtro.getRestauranteId()!= null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}
		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO,StatusPedido.ENTREGUE));

		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
