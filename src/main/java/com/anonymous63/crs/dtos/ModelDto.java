package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Car;
import com.anonymous63.crs.models.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class ModelDto {

    private Long id;

    private String name;

    private Company company;

    private List<Car> cars;
}
