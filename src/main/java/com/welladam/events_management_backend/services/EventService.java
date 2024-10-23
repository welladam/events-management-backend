package com.welladam.events_management_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welladam.events_management_backend.models.Event;
import com.welladam.events_management_backend.models.ImageResponse;
import com.welladam.events_management_backend.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Value("${fivemanage.api.url}")
    private String fiveManageApiUrl;

    @Value("${fivemanage.api.token}")
    private String fiveManageApiToken;

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findActiveEvents() {
        return eventRepository.findByIsActiveTrueOrderByStartDateDesc();
    }

    public List<Event> findUpcomingEvents() {
        return eventRepository.findUpcomingActiveEvents(LocalDate.now());
    }

    public List<Event> findPastEvents() {
        return eventRepository.findPastActiveEvents(LocalDate.now());
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findByIdAndIsActiveTrue(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public String uploadImageToFiveManage(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", fiveManageApiToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(fiveManageApiUrl, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            ImageResponse uploadResponse = objectMapper.readValue(response.getBody(), ImageResponse.class);
            return uploadResponse.getUrl();
        } else {
            throw new IOException("Failed to upload image to FiveManage API");
        }
    }
}

