package ru.practicum.ewm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdatedRequestsDto {
    List<RequestDto> confirmedRequests = new ArrayList<>();
    List<RequestDto> rejectedRequests = new ArrayList<>();
}
