package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRepo extends JpaRepository<Model, Long> {
    Optional<Model> findByName(String name);
}
