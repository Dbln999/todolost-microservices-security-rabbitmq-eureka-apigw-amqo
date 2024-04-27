package com.dbln9.Customer.customer;

import com.dbln9.Customer.Exception.BadRequestException;
import com.dbln9.Customer.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<CustomerResponse> registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .email(customerRegistrationRequest.email())
                .password(passwordEncoder.encode(customerRegistrationRequest.password()))
                .firstname(customerRegistrationRequest.firstname())
                .lastname(customerRegistrationRequest.lastname())
                .role(Role.USER)
                .build();
        Customer save = customerRepository.save(customer);
        String jwtToken = jwtService.generateToken(customer);

        return ResponseEntity.ok(CustomerResponse.builder()
                .customer(save)
                .token(jwtToken)
                .build());
    }

    public ResponseEntity<CustomerResponse> authenticate(CustomerAuthenticateRequest customerAuthenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerAuthenticateRequest.email(),
                        customerAuthenticateRequest.password()
                )
        );
        Customer customer = customerRepository.findCustomerByEmail(customerAuthenticateRequest.email())
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        String jwtToken = jwtService.generateToken(customer);

        return ResponseEntity.ok(
                CustomerResponse.builder()
                        .customer(customer)
                        .token(jwtToken)
                        .build()
        );
    }
}
