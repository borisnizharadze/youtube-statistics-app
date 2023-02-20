package com.assigment.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STATISTICS_UPDATE_EXCHANGE = "exchange_statistics_update";
    public static final String STATISTICS_UPDATE_QUEUE = "/queue/statistics/update";
    public static final String STATISTICS_UPDATE_BINDING_KEY = "statistics.#";

    @Bean
    public Queue queue() {
        return new Queue(STATISTICS_UPDATE_QUEUE, true);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(STATISTICS_UPDATE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(STATISTICS_UPDATE_BINDING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
