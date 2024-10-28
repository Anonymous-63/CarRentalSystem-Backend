package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.RoleDto;
import com.anonymous63.crs.services.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    @Override
    public RoleDto save(RoleDto entity) {
        return null;
    }

    @Override
    public RoleDto update(Long id, RoleDto entity) {
        return null;
    }

    @Override
    public RoleDto findById(Long id) {
        return null;
    }

    @Override
    public List<RoleDto> findAll() {
        return List.of();
    }

    @Override
    public List<RoleDto> enable(List<Long> ids) {
        return List.of();
    }

    @Override
    public List<RoleDto> disable(List<Long> ids) {
        return List.of();
    }

    @Override
    public List<RoleDto> delete(List<Long> ids) {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }
}
