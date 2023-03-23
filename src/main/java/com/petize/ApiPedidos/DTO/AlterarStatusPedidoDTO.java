package com.petize.ApiPedidos.DTO;

import com.petize.ApiPedidos.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlterarStatusPedidoDTO {
    private Long id;
    private StatusPedido status;
}
