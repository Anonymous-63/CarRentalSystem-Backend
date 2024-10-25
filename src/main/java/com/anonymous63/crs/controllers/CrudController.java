package com.anonymous63.crs.controllers;

import com.anonymous63.crs.payloads.response.APIResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<T, ID> {

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<T>> get(@PathVariable ID id);

    @GetMapping("/")
    ResponseEntity<APIResponse<List<T>>> getAll();

    @PostMapping("/")
    ResponseEntity<APIResponse<T>> create(@Valid @RequestBody T entity);

    @PutMapping("/{id}")
    ResponseEntity<APIResponse<T>> update(@PathVariable ID id, @Valid @RequestBody T entity);

    @PostMapping("/enable")
    ResponseEntity<APIResponse<List<T>>> enable(@RequestBody List<ID> ids);

    @PostMapping("/disable")
    ResponseEntity<APIResponse<List<T>>> disable(@RequestBody List<ID> ids);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/")
    ResponseEntity<APIResponse<List<T>>> delete(@RequestBody List<ID> ids);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteAll")
    ResponseEntity<APIResponse<String>> deleteAll();

}
