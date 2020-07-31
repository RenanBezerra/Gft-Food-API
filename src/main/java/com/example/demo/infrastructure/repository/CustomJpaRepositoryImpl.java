package com.example.demo.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.example.demo.domain.repository.CustomJpaRepository;

import lombok.var;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> 
implements CustomJpaRepository<T, ID> {

	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from" + getDomainClass().getName();

		T entity = manager.createQuery(jpql, getDomainClass())
				.setMaxResults(1)
				.getSingleResult();

		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T entity) {

		manager.detach(entity);
	}
}