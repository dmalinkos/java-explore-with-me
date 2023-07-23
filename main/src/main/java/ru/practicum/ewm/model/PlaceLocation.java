package ru.practicum.ewm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "place_locations", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float lat;

    private Float lon;

    private Float radius;
}
