package com.dbln9.Customer.customer;

import com.dbln9.amqp.RabbitMQMessageProducer;
import com.dbln9.clients.ITodo;
import com.dbln9.clients.TodoClient;
import com.dbln9.clients.TodoCreateRequest;
import com.dbln9.clients.UpdateTodoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final TodoClient todoClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer (@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        return customerService.registerCustomer(customerRegistrationRequest);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<CustomerResponse> authenticateCustomer (@RequestBody CustomerAuthenticateRequest customerAuthenticateRequest) {
        return customerService.authenticate(customerAuthenticateRequest);
    }

    @PostMapping("/add-todo")
    public ITodo addTodo(@RequestBody TodoCreateRequest todoCreateRequest) {
        return todoClient.createTodo(todoCreateRequest);
    }

    @GetMapping("/todo/{customerId}")
    public List<ITodo> getTodo (@PathVariable("customerId") Long customerId) {
        return todoClient.getTodo(customerId);
    }

    @PutMapping("/update-todo")
    public ResponseEntity<ITodo> updateTodo(@RequestBody UpdateTodoRequest updateTodoRequest) {
        rabbitMQMessageProducer.publish(
                updateTodoRequest,
                "internal.exchange",
                "internal.todo.routing-key"
        );
        return ResponseEntity.ok().build();
    }
}
