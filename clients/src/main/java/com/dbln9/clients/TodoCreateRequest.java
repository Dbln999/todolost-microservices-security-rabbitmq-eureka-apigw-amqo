package com.dbln9.clients;

public record TodoCreateRequest(Long customerId, String title, String description) {
}
