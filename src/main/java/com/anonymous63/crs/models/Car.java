package com.anonymous63.crs.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Model model;

    private String licensePlate;

    private Boolean isAutoTransmission; // changed to Boolean

    private String color;

    private int year;

    private int seats;

    private String owner;
}
