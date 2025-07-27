package com.banking.accountservice.messaging;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "account.exchange";
    public static final String QUEUE = "account.created.queue";
    public static final String ROUTING_KEY = "account.created";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue accountCreatedQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(accountCreatedQueue())
                .to(directExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}