package com.anonymous63.crs.repositories;

import com.anonymous63.crs.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
