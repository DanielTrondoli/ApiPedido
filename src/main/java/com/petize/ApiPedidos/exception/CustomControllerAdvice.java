package com.petize.ApiPedidos.exception;

import com.petize.ApiPedidos.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleCProdutoNaoEncontradoException(Exception e ){
        var error = new ErrorResponseDTO(LocalDateTime.now(), 404, "NOT_FOUND", e.getMessage());
        return new ResponseEntity<>( error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuantidadeProdutosInvalida.class)
    public ResponseEntity<ErrorResponseDTO> handleQuantidadeProdutosInvalida(Exception e ){
        var error = new ErrorResponseDTO(LocalDateTime.now(), 400, "BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>( error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handlePedidoNaoEncontradoException(Exception e ){
        var error = new ErrorResponseDTO(LocalDateTime.now(), 404, "NOT_FOUND", e.getMessage());
        return new ResponseEntity<>( error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleStatusInvalidoException(Exception e ){
        var error = new ErrorResponseDTO(LocalDateTime.now(), 400, "BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>( error, HttpStatus.BAD_REQUEST);
    }
}
