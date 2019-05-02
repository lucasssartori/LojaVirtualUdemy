package com.sartorilucas.lojavirtualudemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sartorilucas.lojavirtualudemy.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository <Pedido, Integer>{

}
