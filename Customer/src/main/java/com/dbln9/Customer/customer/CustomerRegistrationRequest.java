package com.dbln9.Customer.customer;

public record CustomerRegistrationRequest(String email, String password, String firstname, String lastname) {
}
