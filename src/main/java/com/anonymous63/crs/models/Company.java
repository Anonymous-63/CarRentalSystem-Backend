package com.anonymous63.crs.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // corrected data type

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Car> cars;

}
