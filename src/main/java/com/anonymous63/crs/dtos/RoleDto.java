package com.anonymous63.crs.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class RoleDto {

    private Long id;
    private String name;
    private Set<PrivilegeDto> privileges;
}
