package ru.practicum.ewm.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
