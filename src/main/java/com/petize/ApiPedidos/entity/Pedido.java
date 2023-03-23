package com.petize.ApiPedidos.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;

    @OneToMany(mappedBy="pedido", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemPedido> itens;
    @Column
    private LocalDateTime data;
    @Column
    private StatusPedido status;

}
