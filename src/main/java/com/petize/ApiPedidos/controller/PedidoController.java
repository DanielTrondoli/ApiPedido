package com.petize.ApiPedidos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petize.ApiPedidos.DTO.PedidoDTO;
import com.petize.ApiPedidos.entity.Pedido;
import com.petize.ApiPedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPedido(@RequestBody PedidoDTO pedido){
       service.createPedido(pedido);
    }

    @GetMapping(produces = "application/json")
    public List<Pedido> getPedidos(){
        return service.getPedidos();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Pedido getPedidoById(@PathVariable("id") Long id){
        return service.getPedidoById(id);
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public Pedido updatePedido(@PathVariable("id") Long id, @RequestBody PedidoDTO pedido) {
        return service.updatePedido(id, pedido);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void deletePedido(@PathVariable("id") Long id) {
        service.deletePedido(id);
    }

    @PatchMapping(path = "/{id}") // api/pedido/1?status=pendente
    public void updateStatusPedido(@PathVariable("id") Long id, @RequestParam String status) throws JsonProcessingException {
        service.updateStatusPedido(id, status);
    }
}
