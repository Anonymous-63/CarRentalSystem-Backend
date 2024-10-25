package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.ModelDto;
import com.anonymous63.crs.services.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    @Override
    public ModelDto save(ModelDto entity) {
        return null;
    }

    @Override
    public ModelDto update(Long aLong, ModelDto entity) {
        return null;
    }

    @Override
    public ModelDto findById(Long aLong) {
        return null;
    }

    @Override
    public List<ModelDto> findAll() {
        return List.of();
    }

    @Override
    public List<ModelDto> enable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<ModelDto> disable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<ModelDto> delete(List<Long> longs) {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }
}
