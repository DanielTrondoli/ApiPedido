package com.petize.ApiPedidos.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.petize.ApiPedidos.DTO.AlterarStatusPedidoDTO;
import com.petize.ApiPedidos.exception.PedidoNaoEncontradoException;
import com.petize.ApiPedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static com.petize.ApiPedidos.rabbitmq.RabbitMQConfig.ALTERAR_STATUS_PEDIDO_QUEUE_NAME;

@Component
public class AlterarStausPedidoReciver {

    @Autowired
    private PedidoRepository pedidoRepository;

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    private static final Logger LOG = LoggerFactory.getLogger(AlterarStausPedidoReciver.class);
    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) throws JsonProcessingException {
        LOG.info("Queue {}: Processando mensasagem: {}", ALTERAR_STATUS_PEDIDO_QUEUE_NAME, message);

        var dados = objectMapper.readValue(message, AlterarStatusPedidoDTO.class);
        var pedido = pedidoRepository.findById(dados.getId())
                .orElseThrow(() -> new PedidoNaoEncontradoException(dados.getId()));

        pedido.setStatus(dados.getStatus());
        pedidoRepository.save(pedido);

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
