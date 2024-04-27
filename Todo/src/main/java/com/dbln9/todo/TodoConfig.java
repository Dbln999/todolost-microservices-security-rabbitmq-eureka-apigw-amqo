package com.dbln9.todo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.todo}")
    private String todoQueue;

    @Value("${rabbitmq.routing-keys.internal-todo}")
    private String internalTodoRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue todoQueue() {
        return new Queue(this.todoQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(todoQueue())
                .to(internalTopicExchange())
                .with(this.internalTodoRoutingKey);
    }


    public String getInternalExchange() {
        return internalExchange;
    }

    public String getTodoQueue() {
        return todoQueue;
    }

    public String getInternalTodoRoutingKey() {
        return internalTodoRoutingKey;
    }
}
