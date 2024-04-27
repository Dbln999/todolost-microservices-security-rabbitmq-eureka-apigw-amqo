package com.dbln9.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ITodo {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Long customerId;
}
