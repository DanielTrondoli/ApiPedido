package com.petize.ApiPedidos.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(Long id) {
        super(String.format("Pedido %d nao foi encontrado", id));
    }
}
