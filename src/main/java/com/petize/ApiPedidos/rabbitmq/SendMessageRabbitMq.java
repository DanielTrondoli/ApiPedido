package com.petize.ApiPedidos.rabbitmq;

import com.petize.ApiPedidos.broker.SendMessageBroker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.petize.ApiPedidos.rabbitmq.RabbitMQConfig.ALTERAR_STATUS_PEDIDO_ROUTING_KEY;
import static com.petize.ApiPedidos.rabbitmq.RabbitMQConfig.DIRECT_EXCHANGE_NAME;

@Component
public class SendMessageRabbitMq implements SendMessageBroker {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void updateStatusPedido(Object message) {
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME, ALTERAR_STATUS_PEDIDO_ROUTING_KEY, message);
    }
}
