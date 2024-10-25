package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CompanyDto;
import com.anonymous63.crs.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public CompanyDto save(CompanyDto entity) {
        return null;
    }

    @Override
    public CompanyDto update(Long aLong, CompanyDto entity) {
        return null;
    }

    @Override
    public CompanyDto findById(Long aLong) {
        return null;
    }

    @Override
    public List<CompanyDto> findAll() {
        return List.of();
    }

    @Override
    public List<CompanyDto> enable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<CompanyDto> disable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<CompanyDto> delete(List<Long> longs) {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }
}
