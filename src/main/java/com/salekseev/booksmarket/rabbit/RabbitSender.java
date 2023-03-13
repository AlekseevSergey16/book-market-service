package com.salekseev.booksmarket.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salekseev.booksmarket.model.Order;
import com.salekseev.booksmarket.rabbit.event.OrderCreatedMessage;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper objectMapper;

    public RabbitSender(RabbitTemplate rabbitTemplate, Queue queue, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void send(Order order) {
        final String orderJson = objectMapper.writeValueAsString(order);
        rabbitTemplate.convertAndSend(this.queue.getName(), orderJson);
    }

    @SneakyThrows
    public void send(OrderCreatedMessage orderCreatedEvent) {
        final String orderJson = objectMapper.writeValueAsString(orderCreatedEvent);
        rabbitTemplate.convertAndSend(this.queue.getName(), orderJson);
    }

}
