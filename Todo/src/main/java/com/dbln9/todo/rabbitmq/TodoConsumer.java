package com.dbln9.todo.rabbitmq;

import com.dbln9.clients.UpdateTodoRequest;
import com.dbln9.todo.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TodoConsumer {

    private final TodoService todoService;

    @RabbitListener(queues = "${rabbitmq.queues.todo}")
    public void consumer(UpdateTodoRequest updateTodoRequest) {
        log.info("Consumed {} from queue", updateTodoRequest);
        todoService.updateTodo(updateTodoRequest);
    }
}
