package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.ModelDto;
import com.anonymous63.crs.payloads.response.APIResponse;
import com.anonymous63.crs.services.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController implements CrudController<ModelDto, Long> {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public ResponseEntity<APIResponse<ModelDto>> get(Long id) {
        ModelDto retrievedModel = this.modelService.findById(id);
        APIResponse<ModelDto> response = APIResponse.<ModelDto>builder()
                .status(true)
                .message("Model fetched successfully")
                .results(retrievedModel)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<ModelDto>>> getAll() {
        List<ModelDto> allModels = this.modelService.findAll();
        APIResponse<List<ModelDto>> response = APIResponse.<List<ModelDto>>builder()
                .status(true)
                .message("Models fetched successfully")
                .results(allModels)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<ModelDto>> create(ModelDto entity) {
        ModelDto createdModel = this.modelService.save(entity);

        APIResponse<ModelDto> response = APIResponse.<ModelDto>builder()
                .status(true)
                .message("Model created successfully")
                .results(createdModel)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<ModelDto>> update(Long id, ModelDto entity) {
        ModelDto updatedModel = this.modelService.update(id, entity);
        APIResponse<ModelDto> response = APIResponse.<ModelDto>builder()
                .status(true)
                .message("Model updated successfully")
                .results(updatedModel)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<List<ModelDto>>> enable(List<Long> ids) {
        List<ModelDto> enabledModels = this.modelService.enable(ids);
        APIResponse<List<ModelDto>> response = APIResponse.<List<ModelDto>>builder()
                .status(true)
                .message("Models enabled successfully")
                .results(enabledModels)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<ModelDto>>> disable(List<Long> ids) {
        List<ModelDto> disabledModels = this.modelService.disable(ids);
        APIResponse<List<ModelDto>> response = APIResponse.<List<ModelDto>>builder()
                .status(true)
                .message("Models disabled successfully")
                .results(disabledModels)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<ModelDto>>> delete(List<Long> ids) {
        List<ModelDto> deletedModels = this.modelService.delete(ids);
        APIResponse<List<ModelDto>> response = APIResponse.<List<ModelDto>>builder()
                .status(true)
                .message("Models deleted successfully")
                .results(deletedModels)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<String>> deleteAll() {
        this.modelService.deleteAll();
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("All Models deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
