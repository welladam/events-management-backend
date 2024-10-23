package com.welladam.events_management_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(nullable = true)
    private String description;

    @NotBlank(message = "Start Date is mandatory")
    private OffsetDateTime startDate;
    @NotBlank(message = "End Date is mandatory")
    private OffsetDateTime endDate;

    @Column(nullable = true)
    private Double price;
    @Column(nullable = true)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Status status;

    public enum Status {
        started, completed, paused
    }

    private Boolean isActive = true;
}
