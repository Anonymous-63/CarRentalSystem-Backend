package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepo extends JpaRepository<Car, Long> {
    Optional<Car> findByName(String name);
}
