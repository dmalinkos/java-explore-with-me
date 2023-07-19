package ru.practicum.ewm.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.ewm.model.enums.RequestUpdateStatus;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RequestUpdateDto {
    List<Long> requestIds;
    RequestUpdateStatus status;
}
