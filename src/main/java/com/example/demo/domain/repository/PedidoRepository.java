package com.example.demo.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
