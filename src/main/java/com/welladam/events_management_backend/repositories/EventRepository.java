package com.welladam.events_management_backend.repositories;

import com.welladam.events_management_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsActiveTrueOrderByStartDateDesc();
    Optional<Event> findByIdAndIsActiveTrue(Long id);
}
