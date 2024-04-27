package com.dbln9.todo;

import com.dbln9.clients.TodoCreateRequest;
import com.dbln9.clients.UpdateTodoRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoRepository.findAll());
    }

    public ResponseEntity<Todo> createTodo(TodoCreateRequest todoCreateRequest) {
        Todo todo = Todo.builder()
                .customerId(todoCreateRequest.customerId())
                .title(todoCreateRequest.title())
                .description(todoCreateRequest.description())
                .completed(false)
                .build();
        log.info("Creating new todo: {}", todo);
        Todo save = todoRepository.save(todo);
        return ResponseEntity.ok(save);
    }

    public ResponseEntity<List<Todo>> getTodosByCustomerId(Long customerId) {
        List<Todo> todos = todoRepository.findTodosByCustomerId(customerId);

        if(todos.isEmpty()) {
            throw new RuntimeException("Todo not found");
        }

        return ResponseEntity.ok(todos);
    }

    public ResponseEntity<Todo> updateTodo(UpdateTodoRequest updateTodoRequest) {
        Todo todo = todoRepository.findById(updateTodoRequest.id()).orElse(null);
        if(todo == null) {
            throw new RuntimeException("Todo not found");
        }

        boolean changes = false;

        if(updateTodoRequest.title() != null && !updateTodoRequest.title().equals(todo.getTitle())) {
            todo.setTitle(updateTodoRequest.title());
            changes = true;
        }

        if(updateTodoRequest.description() != null && !updateTodoRequest.description().equals(todo.getDescription())) {
            todo.setDescription(updateTodoRequest.description());
            changes = true;
        }

        if(updateTodoRequest.completed() != todo.getCompleted()) {
            todo.setCompleted(updateTodoRequest.completed());
            changes = true;
        }
        if (!changes) {
            throw new RuntimeException("no data changes found");
        }

        Todo save = todoRepository.save(todo);
        return ResponseEntity.ok(save);
    }
}
