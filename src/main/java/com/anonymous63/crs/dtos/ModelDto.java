package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Car;
import com.anonymous63.crs.models.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ModelDto {

    private Long id;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, max = 20, message = "Name must be between {min} and {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z0-9,.\\s]+$", message = "Address can only contain letters, numbers, commas, periods, and spaces.")
    private String name;

    private CompanyDto company;

    @JsonProperty(defaultValue = "true")
    private Boolean enabled = true;
}
