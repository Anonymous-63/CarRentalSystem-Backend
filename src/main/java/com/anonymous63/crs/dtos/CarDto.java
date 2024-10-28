package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Model;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarDto {

    private Long id;

    private ModelDto model;

    private LocalDateTime registerDatetime;

    private String licensePlate;

    private String color;

    private List<String> photos;

    private int seats;

    private UserDto owner;

}
