package com.dbln9.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "todo", url = "http://localhost:8081", path = "/api/v1")
public interface TodoClient {

    @PostMapping("/todos")
    ITodo createTodo(@RequestBody TodoCreateRequest todoCreateRequest);

    @GetMapping("/todos/{customerId}")
    List<ITodo> getTodo(@PathVariable("customerId") Long customerId);

    @PutMapping("/todos")
    ResponseEntity<ITodo> updateTodo(@RequestBody UpdateTodoRequest updateTodoRequest);
}
