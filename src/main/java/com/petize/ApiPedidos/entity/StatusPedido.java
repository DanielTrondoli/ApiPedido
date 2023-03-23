package com.petize.ApiPedidos.entity;

public enum StatusPedido {
    PENDENTE("pendente"),
    PROCESSANDO("processando"),
    CONCLUIDO("concluido");


    private final String descricao;

    StatusPedido(String desccricao) {
        this.descricao = desccricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
