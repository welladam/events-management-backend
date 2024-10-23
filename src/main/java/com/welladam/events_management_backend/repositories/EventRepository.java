package com.welladam.events_management_backend.repositories;

import com.welladam.events_management_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsActiveTrueOrderByStartDateDesc();

    @Query("SELECT e FROM Event e WHERE e.isActive = true AND DATE(e.startDate) < :today ORDER BY e.startDate DESC")
    List<Event> findPastActiveEvents(@Param("today") LocalDate today);

    @Query("SELECT e FROM Event e WHERE e.isActive = true AND DATE(e.startDate) >= :today ORDER BY e.startDate ASC")
    List<Event> findUpcomingActiveEvents(@Param("today") LocalDate today);

    Optional<Event> findByIdAndIsActiveTrue(Long id);
}
