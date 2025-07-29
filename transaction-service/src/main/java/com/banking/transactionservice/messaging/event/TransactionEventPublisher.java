package com.banking.transactionservice.messaging.event;

import com.banking.transactionservice.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TransactionEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(TransactionCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TRANSACTION_EXCHANGE,
                RabbitMQConfig.TRANSACTION_ROUTING_KEY,
                event
        );
    }
}