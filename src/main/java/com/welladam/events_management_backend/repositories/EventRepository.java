package com.welladam.events_management_backend.repositories;

import com.welladam.events_management_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsActiveTrue();
}
