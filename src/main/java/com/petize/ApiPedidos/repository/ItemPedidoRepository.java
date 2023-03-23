package com.petize.ApiPedidos.repository;

import com.petize.ApiPedidos.entity.ItemPedido;
import com.petize.ApiPedidos.entity.ItemPedidoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoId> {

    void deleteByIdPedidoId(Long pedidoId);
}
