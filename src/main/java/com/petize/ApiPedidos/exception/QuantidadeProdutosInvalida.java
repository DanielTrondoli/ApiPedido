package com.petize.ApiPedidos.exception;

public class QuantidadeProdutosInvalida extends RuntimeException {
    public QuantidadeProdutosInvalida() {
        super("A quantidade de um produto deve ser maior que zero");
    }
}
