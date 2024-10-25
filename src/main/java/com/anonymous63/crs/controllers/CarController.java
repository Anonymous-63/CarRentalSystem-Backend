package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.CarDto;
import com.anonymous63.crs.payloads.response.APIResponse;
import com.anonymous63.crs.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController implements CrudController<CarDto, Long> {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Override
    public ResponseEntity<APIResponse<CarDto>> get(Long id) {
        CarDto retrievedCar = this.carService.findById(id);
        APIResponse<CarDto> response = APIResponse.<CarDto>builder()
                .status(true)
                .message("Car fetched successfully")
                .results(retrievedCar)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CarDto>>> getAll() {
        List<CarDto> allCars = this.carService.findAll();
        APIResponse<List<CarDto>> response = APIResponse.<List<CarDto>>builder()
                .status(true)
                .message("Cars fetched successfully")
                .results(allCars)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<CarDto>> create(CarDto entity) {
        CarDto createdCar = this.carService.save(entity);

        APIResponse<CarDto> response = APIResponse.<CarDto>builder()
                .status(true)
                .message("Car created successfully")
                .results(createdCar)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<CarDto>> update(Long id, CarDto entity) {
        CarDto updatedCar = this.carService.update(id, entity);
        APIResponse<CarDto> response = APIResponse.<CarDto>builder()
                .status(true)
                .message("Car updated successfully")
                .results(updatedCar)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<List<CarDto>>> enable(List<Long> ids) {
        List<CarDto> enabledCars = this.carService.enable(ids);
        APIResponse<List<CarDto>> response = APIResponse.<List<CarDto>>builder()
                .status(true)
                .message("Cars enabled successfully")
                .results(enabledCars)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CarDto>>> disable(List<Long> ids) {
        List<CarDto> disabledCars = this.carService.disable(ids);
        APIResponse<List<CarDto>> response = APIResponse.<List<CarDto>>builder()
                .status(true)
                .message("Cars disabled successfully")
                .results(disabledCars)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CarDto>>> delete(List<Long> ids) {
        List<CarDto> deletedCars = this.carService.delete(ids);
        APIResponse<List<CarDto>> response = APIResponse.<List<CarDto>>builder()
                .status(true)
                .message("Cars deleted successfully")
                .results(deletedCars)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<String>> deleteAll() {
        this.carService.deleteAll();
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("All cars deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
