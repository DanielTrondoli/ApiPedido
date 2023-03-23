package com.petize.ApiPedidos.controller;

import com.petize.ApiPedidos.DTO.ProdutoDTO;
import com.petize.ApiPedidos.entity.Produto;
import com.petize.ApiPedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping(produces = "application/json")
    public Produto createProduto(@RequestBody ProdutoDTO produto){
       return service.createProduto(produto);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Produto getProdutoById(@PathVariable("id") Long id){
        return service.getProdutoById(id);
    }

    @GetMapping(produces = "application/json")
    public List<Produto> getProdutos(){
        return service.getProdutos();
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public Produto updateProduto(@PathVariable("id") Long id, @RequestBody ProdutoDTO produto) {
        return service.updateProduto(id, produto);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void deleteProduto(@PathVariable("id") Long id) {
        service.deleteProduto(id);
    }
}
