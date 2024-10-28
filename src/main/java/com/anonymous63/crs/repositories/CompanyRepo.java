package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
