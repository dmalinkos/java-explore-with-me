package ru.practicum.ewm.model;

import lombok.*;
import ru.practicum.ewm.model.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private Long confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private Boolean paid;

    private int participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private EventState state;

    private String title;

    private Long views;

//    public Event(Long id, String annotation, Category category, Long confirmedRequests, LocalDateTime createdOn, String description, LocalDateTime eventDate, User initiator, Location location, Boolean paid, Integer participantLimit, LocalDateTime publishedOn, Boolean requestModeration, EventState eventState, String title, Long views) {
//        this.annotation = annotation;
//        this.category = category;
//        this.confirmedRequests = confirmedRequests;
//        if (createdOn == null) {
//            this.createdOn = LocalDateTime.now();
//        } else {
//            this.createdOn = createdOn;
//        }
//
//        this.description = description;
//        this.eventDate = eventDate;
//        this.id = id;
//        this.initiator = initiator;
//        this.location = location;
//        this.paid = paid;
//        this.participantLimit = participantLimit;
//        this.publishedOn = publishedOn;
//        if (requestModeration == null) {
//            this.requestModeration = true;
//        } else {
//            this.requestModeration = requestModeration;
//        }
//        if (eventState == null) {
//            this.state = EventState.PENDING;
//        } else {
//            this.state = eventState;
//        }
//        this.title = title;
//        this.views = views;
//    }
}
