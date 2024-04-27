package com.dbln9.clients;

public record UpdateTodoRequest(Long id, String title, String description, boolean completed) {
}
