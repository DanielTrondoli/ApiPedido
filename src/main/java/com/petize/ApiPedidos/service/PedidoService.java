package com.petize.ApiPedidos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.petize.ApiPedidos.DTO.AlterarStatusPedidoDTO;
import com.petize.ApiPedidos.DTO.ItemPedidoDTO;
import com.petize.ApiPedidos.DTO.PedidoDTO;
import com.petize.ApiPedidos.broker.SendMessageBroker;
import com.petize.ApiPedidos.entity.ItemPedido;
import com.petize.ApiPedidos.entity.Pedido;
import com.petize.ApiPedidos.entity.StatusPedido;
import com.petize.ApiPedidos.exception.PedidoNaoEncontradoException;
import com.petize.ApiPedidos.exception.ProdutoNaoEncontradoException;
import com.petize.ApiPedidos.exception.QuantidadeProdutosInvalida;
import com.petize.ApiPedidos.exception.StatusInvalidoException;
import com.petize.ApiPedidos.repository.ItemPedidoRepository;
import com.petize.ApiPedidos.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ItemPedidoRepository itemPedidoRepo;

    @Autowired
    private SendMessageBroker sendMessage;

    private static final ObjectMapper JSON_MAPPER = JsonMapper.builder().findAndAddModules().build();

    private static final ModelMapper MAPPER = new ModelMapper();

    @Transactional
    public void createPedido(PedidoDTO pedidoDTO) {

        var pedido = MAPPER.map(pedidoDTO, Pedido.class);
        pedidoRepo.save(pedido);

        var itensPedido = createItensPedido(pedido, pedidoDTO.getItens());
        itemPedidoRepo.saveAll(itensPedido);
    }

    private List<ItemPedido> createItensPedido(Pedido pedido, List<ItemPedidoDTO> itensDTO) {

        List<ItemPedido> itens = new ArrayList<>();

        for (var item : itensDTO) {
            if (item.getQuantidade() <= 0) {
                throw new QuantidadeProdutosInvalida();
            }

            var itemRepitidoOpt = buscaItemRepetido(item.getIdProduto(), itens);

            if (itemRepitidoOpt.isPresent()) {
                var itemRepstido = itemRepitidoOpt.get();
                itemRepstido.setQuantidade(itemRepstido.getQuantidade() + item.getQuantidade());
            } else {
                var produto = produtoService.getProdutoById(item.getIdProduto());
                if (produto != null) {
                    itens.add(new ItemPedido(pedido, produto, item.getQuantidade()));
                } else {
                    throw new ProdutoNaoEncontradoException(item.getIdProduto());
                }
            }
        }
        return itens;
    }

    private Optional<ItemPedido> buscaItemRepetido(Long idProduto, List<ItemPedido> itens) {
        return itens.stream()
                .filter(i -> i.getId().getProdutoId().equals(idProduto))
                .findFirst();
    }

    public List<Pedido> getPedidos() {
        return pedidoRepo.findAll();
    }

    public Pedido getPedidoById(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional
    public Pedido updatePedido(Long id, PedidoDTO pedidoDTO) {

        verificaPedidoValidoOrThrowException(id);

        var pedido = new Pedido();
        pedido.setPedidoId(id);
        pedido.setData(pedidoDTO.getData());
        pedido.setStatus(pedidoDTO.getStatus());
        pedidoRepo.save(pedido);

        var itens = createItensPedido(pedido, pedidoDTO.getItens());
        itemPedidoRepo.deleteByIdPedidoId(id);
        itemPedidoRepo.saveAll(itens);
        pedido.setItens(itens);

        return pedido;

    }

    @Transactional
    public void deletePedido(Long id) {
        verificaPedidoValidoOrThrowException(id);

        itemPedidoRepo.deleteByIdPedidoId(id);
        pedidoRepo.deleteById(id);

    }

    public void updateStatusPedido(Long id, String status) throws JsonProcessingException {

        verificaPedidoValidoOrThrowException(id);
        StatusPedido statusPedido = validaStatusPedidoOrThrowException(status);

        var mensagem = JSON_MAPPER.writeValueAsString(new AlterarStatusPedidoDTO(id, statusPedido));
        sendMessage.updateStatusPedido(mensagem);
    }

    private StatusPedido validaStatusPedidoOrThrowException(String status) {
        try {
            return StatusPedido.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusInvalidoException(status);
        }
    }

    private void verificaPedidoValidoOrThrowException(Long id) {
        if (!pedidoRepo.existsById(id)) {
            throw new PedidoNaoEncontradoException(id);
        }
    }

    public void updateStatusPedido(AlterarStatusPedidoDTO novoStatus) {

        var pedido = pedidoRepo.findById(novoStatus.getId())
                .orElseThrow(() -> new PedidoNaoEncontradoException(novoStatus.getId()));

        pedido.setStatus(novoStatus.getStatus());
        pedidoRepo.save(pedido);
    }
}
