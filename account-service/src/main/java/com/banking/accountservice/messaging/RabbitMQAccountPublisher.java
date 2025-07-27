package com.banking.accountservice.messaging;
import com.banking.accountservice.messaging.event.AccountCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAccountPublisher implements AccountEventPublisher  {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQAccountPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishAccountCreated(AccountCreatedEvent event) {
        rabbitTemplate.convertAndSend("account.exchange", "account.created", event);
    }
}
