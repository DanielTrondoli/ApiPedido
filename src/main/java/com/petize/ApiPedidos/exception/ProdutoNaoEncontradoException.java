package com.petize.ApiPedidos.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Long id) {
        super(String.format("Produto %d nao Encontrado", id));
    }
}
