package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Model;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarDto {

    private Long id;

    private Model model;
    @NotNull(message = "Color cannot be null")
    @Size(max = 20, message = "Color must be at most {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Color must contain only alphabetic characters.")
    private String color;

    @NotNull(message = "Seats cannot be null")
    @Positive(message = "Must be positive")
    @Range(min = 4, max = 99, message = "Seats must be between {min} and {max}.")
    private int seats;

    @NotNull(message = "Transmission cannot be null")
    @Size(max = 20, message = "Transmission must be at most {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Transmission must contain only alphabetic characters.")
    private String transmission;

    @NotNull(message = "Fuel type cannot be null")
    @Size(max = 20, message = "Fuel type must be at most {max} characters long.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Fuel type must contain only alphabetic characters.")
    private String fuelType;

    @NotNull(message = "License plate cannot be null")
    @Size(max = 20, message = "License plate must be at most {max} characters long")
    @Pattern(regexp = "^[a-zA-z0-9-]+$", message = "Username can only contain letters, numbers, and dash.")
    private String licensePlate;

    @NotNull(message = "Registration date cannot be mull")
    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDateTime registrationDate;

    private Boolean enabled = true;

}
