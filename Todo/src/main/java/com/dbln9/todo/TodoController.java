package com.dbln9.todo;

import com.dbln9.clients.TodoCreateRequest;
import com.dbln9.clients.UpdateTodoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/todos")
@RestController
@AllArgsConstructor
@Data
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoCreateRequest todoCreateRequest) {
        return todoService.createTodo(todoCreateRequest);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<List<Todo>> getTodo(@PathVariable("customerId") Long customerId) {
        return todoService.getTodosByCustomerId(customerId);
    }

    @PutMapping()
    public ResponseEntity<Todo> updateTodo(@RequestBody UpdateTodoRequest updateTodoRequest) {
        return todoService.updateTodo(updateTodoRequest);

    }

}
