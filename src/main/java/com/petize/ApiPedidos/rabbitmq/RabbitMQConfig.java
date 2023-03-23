package com.petize.ApiPedidos.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE_NAME = "api-pedido";
    public static final String ALTERAR_STATUS_PEDIDO_QUEUE_NAME = "alterar_status_pedido_queue";
    public static final String ALTERAR_STATUS_PEDIDO_ROUTING_KEY = "alterar_status_pedido";

    @Bean
    Queue queue() {
        return new Queue(ALTERAR_STATUS_PEDIDO_QUEUE_NAME, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ALTERAR_STATUS_PEDIDO_ROUTING_KEY);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AlterarStausPedidoReciver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                     MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ALTERAR_STATUS_PEDIDO_QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
