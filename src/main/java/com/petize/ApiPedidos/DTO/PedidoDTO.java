package com.petize.ApiPedidos.DTO;

import com.petize.ApiPedidos.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private LocalDateTime data;
    private StatusPedido status;
    private List<ItemPedidoDTO> itens;

}
