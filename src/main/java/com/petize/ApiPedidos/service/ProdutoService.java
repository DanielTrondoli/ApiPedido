package com.petize.ApiPedidos.service;

import com.petize.ApiPedidos.DTO.ProdutoDTO;
import com.petize.ApiPedidos.entity.Produto;
import com.petize.ApiPedidos.exception.ProdutoNaoEncontradoException;
import com.petize.ApiPedidos.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    private static final ModelMapper MAPPER = new ModelMapper();

    public Produto createProduto(ProdutoDTO produto) {
        var novoProduto = MAPPER.map(produto, Produto.class);
        repository.save(novoProduto);
        return novoProduto;
    }

    public List<Produto> getProdutos() {
        return repository.findAll();
    }

    public Produto getProdutoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new ProdutoNaoEncontradoException(id));
    }

    public Produto updateProduto(Long id, ProdutoDTO produtoDTO) {

        if (repository.findById(id).isEmpty()) {
            throw new ProdutoNaoEncontradoException(id);
        }

        var produto = MAPPER.map(produtoDTO, Produto.class);
        produto.setProdutoId(id);
        repository.save(produto);
        return produto;
    }

    public void deleteProduto(Long id) {
        repository.deleteById(id);
    }
}
