package com.dbln9.todo;

import com.dbln9.clients.ITodo;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Todo extends ITodo{
    @Id
    @GeneratedValue(generator = "todo_id_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "todo_id_sequence", name = "todo_id_sequence")
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Long customerId;
}
