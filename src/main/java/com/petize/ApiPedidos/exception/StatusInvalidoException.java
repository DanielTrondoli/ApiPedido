package com.petize.ApiPedidos.exception;

public class StatusInvalidoException extends RuntimeException {
    public StatusInvalidoException(String status) {
        super(String.format("Status \"%s\" não existe", status));
    }
}
