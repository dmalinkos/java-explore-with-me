package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;
}
