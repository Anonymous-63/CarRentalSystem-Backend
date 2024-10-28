package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.CompanyDto;
import com.anonymous63.crs.payloads.response.APIResponse;
import com.anonymous63.crs.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController implements CrudController<CompanyDto, Long> {
    
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public ResponseEntity<APIResponse<CompanyDto>> get(Long id) {
        CompanyDto retrievedCompany = this.companyService.findById(id);
        APIResponse<CompanyDto> response = APIResponse.<CompanyDto>builder()
                .status(true)
                .message("Company fetched successfully")
                .results(retrievedCompany)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CompanyDto>>> getAll() {
        List<CompanyDto> allCompanys = this.companyService.findAll();
        APIResponse<List<CompanyDto>> response = APIResponse.<List<CompanyDto>>builder()
                .status(true)
                .message("Companys fetched successfully")
                .results(allCompanys)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<CompanyDto>> create(CompanyDto entity) {
        CompanyDto createdCompany = this.companyService.save(entity);

        APIResponse<CompanyDto> response = APIResponse.<CompanyDto>builder()
                .status(true)
                .message("Company created successfully")
                .results(createdCompany)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<CompanyDto>> update(Long id, CompanyDto entity) {
        CompanyDto updatedCompany = this.companyService.update(id, entity);
        APIResponse<CompanyDto> response = APIResponse.<CompanyDto>builder()
                .status(true)
                .message("Company updated successfully")
                .results(updatedCompany)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<List<CompanyDto>>> enable(List<Long> ids) {
        List<CompanyDto> enabledCompanies = this.companyService.enable(ids);
        APIResponse<List<CompanyDto>> response = APIResponse.<List<CompanyDto>>builder()
                .status(true)
                .message("Companies enabled successfully")
                .results(enabledCompanies)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CompanyDto>>> disable(List<Long> ids) {
        List<CompanyDto> disabledCompanies = this.companyService.disable(ids);
        APIResponse<List<CompanyDto>> response = APIResponse.<List<CompanyDto>>builder()
                .status(true)
                .message("Companies disabled successfully")
                .results(disabledCompanies)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<CompanyDto>>> delete(List<Long> ids) {
        List<CompanyDto> deletedCompanies = this.companyService.delete(ids);
        APIResponse<List<CompanyDto>> response = APIResponse.<List<CompanyDto>>builder()
                .status(true)
                .message("Companies deleted successfully")
                .results(deletedCompanies)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<String>> deleteAll() {
        this.companyService.deleteAll();
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("All Companies deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
