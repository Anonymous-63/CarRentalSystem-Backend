package com.anonymous63.crs.dtos;

import com.anonymous63.crs.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
}
