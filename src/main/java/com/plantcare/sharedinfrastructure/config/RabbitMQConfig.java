package com.plantcare.sharedinfrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "plant-care.exchange";
    public static final String QUEUE_EMAIL = "email.notifications";
    public static final String QUEUE_TASK = "task.calculation";
    public static final String ROUTING_KEY_EMAIL = "email.routing.key";
    public static final String ROUTING_KEY_TASK = "task.routing.key";

    @Bean
    public TopicExchange plantCareExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL);
    }

    @Bean
    public Queue taskQueue() {
        return new Queue(QUEUE_TASK);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange plantCareExchange) {
        return BindingBuilder.bind(emailQueue).to(plantCareExchange).with(ROUTING_KEY_EMAIL);
    }

    @Bean
    public Binding taskBinding(Queue taskQueue, TopicExchange plantCareExchange) {
        return BindingBuilder.bind(taskQueue).to(plantCareExchange).with(ROUTING_KEY_TASK);
    }
}



