package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    @NotNull
    private String username;
    private String password;
    private Set<Role> roles;
}
