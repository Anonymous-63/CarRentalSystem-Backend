package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Car;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private Long id;

    private String name;

    private List<Car> cars;
}
