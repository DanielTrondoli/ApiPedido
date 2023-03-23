package com.petize.ApiPedidos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="item_pedido")
public class ItemPedido {

    @EmbeddedId
    @JsonIgnore
    private ItemPedidoId id;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("pedidoId")
    @JsonBackReference
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("produtoId")
    private Produto produto;
    @Column
    private int quantidade;

    public ItemPedido(Pedido pedido, Produto produto, int quantidade) {
        this.id = new ItemPedidoId(pedido.getPedidoId(), produto.getProdutoId());
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
