package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CarDto;
import com.anonymous63.crs.services.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Override
    public CarDto save(CarDto entity) {
        return null;
    }

    @Override
    public CarDto update(Long aLong, CarDto entity) {
        return null;
    }

    @Override
    public CarDto findById(Long aLong) {
        return null;
    }

    @Override
    public List<CarDto> findAll() {
        return List.of();
    }

    @Override
    public List<CarDto> enable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<CarDto> disable(List<Long> longs) {
        return List.of();
    }

    @Override
    public List<CarDto> delete(List<Long> longs) {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }
}
