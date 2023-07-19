package ru.practicum.ewm.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class StatisticClient {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate restTemplate;

    public StatisticClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        this.restTemplate = builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void addEndpointHit(String app, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .app(app)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(endpointHitDto, headers);
        restTemplate.exchange("/hit", HttpMethod.POST, requestEntity, EndpointHitDto.class);
    }

    public List<ViewStats> getStatsView(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        UriComponents uri = UriComponentsBuilder.newInstance().path("/stats")
                .queryParam("start", start.format(DATETIME_FORMATTER))
                .queryParam("end", end.format(DATETIME_FORMATTER))
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .build();
        ResponseEntity<Object> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null, Object.class);
        ObjectMapper om = new ObjectMapper();
        return om.convertValue(response.getBody(), new TypeReference<>() {
        });
    }
}
