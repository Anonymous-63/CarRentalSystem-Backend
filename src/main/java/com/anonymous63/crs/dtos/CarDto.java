package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Company;
import com.anonymous63.crs.models.Model;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CarDto {

    private Long id;

    private String name;

    private Company company;

    private Model model;

    private String licensePlate;

    private Boolean isAutoTransmission; // changed to Boolean

    private String color;

    private int year;

    private int seats;

    private String owner;

}
