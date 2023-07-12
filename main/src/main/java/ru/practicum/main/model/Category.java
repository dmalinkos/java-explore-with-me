package ru.practicum.main.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}

