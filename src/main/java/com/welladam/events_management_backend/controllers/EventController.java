package com.welladam.events_management_backend.controllers;

import com.welladam.events_management_backend.models.Event;
import com.welladam.events_management_backend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findActiveEvents();
    }

    @GetMapping("/upcoming")
    public List<Event> getAllUpcomingEvents() {
        return eventService.findUpcomingEvents();
    }

    @GetMapping("/past")
    public List<Event> getAllPastEvents() {
        return eventService.findPastEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestPart Event event, @RequestPart(required = false) MultipartFile file) {
        if (event.getEndDate().isBefore(event.getStartDate())) {
            return ResponseEntity.badRequest().build();
        }

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = eventService.uploadImageToFiveManage(file);
                event.setImageUrl(imageUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return new ResponseEntity<>(eventService.save(event), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestPart Event event, @RequestPart(required = false) MultipartFile file) {
        if (event.getEndDate().isBefore(event.getStartDate())) {
            return ResponseEntity.badRequest().body("End date cannot be before start date.");
        }

        Optional<Event> existingEventOpt = eventService.findById(id);

        if (existingEventOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event existingEvent = existingEventOpt.get();
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setPrice(event.getPrice());
        existingEvent.setStatus(event.getStatus());

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = eventService.uploadImageToFiveManage(file);
                existingEvent.setImageUrl(imageUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Event updatedEvent = eventService.save(existingEvent);
        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        return eventService.findById(id)
                .map(event -> {
                    event.setIsActive(false);
                    eventService.save(event);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

