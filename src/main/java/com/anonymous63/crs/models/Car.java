package com.anonymous63.crs.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Model model;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(nullable = false, length = 2)
    private int seats;

    @Column(nullable = false, length = 20)
    private String transmission;

    @Column(nullable = false, length = 20)
    private String fuelType;

    @Column(nullable = false, length = 20)
    private String licensePlate;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enabled = true;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
