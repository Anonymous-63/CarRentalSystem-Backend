package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CompanyDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Company;
import com.anonymous63.crs.repositories.CompanyRepo;
import com.anonymous63.crs.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepo companyRepo, ModelMapper modelMapper) {
        this.companyRepo = companyRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CompanyDto save(CompanyDto entity) {
        companyRepo.findByName(entity.getName())
                .ifPresent(company -> {
                    throw new DuplicateResourceException(Company.class.getSimpleName(), entity.getName());
                });

        if (entity.getId() != null) {
            companyRepo.findById(entity.getId())
                    .ifPresent(company -> {
                        throw new DuplicateResourceException(Company.class.getSimpleName(), entity.getId());
                    });
        }

        // Map UserDto to User entity
        Company company = modelMapper.map(entity, Company.class);
        Company savedCompany = companyRepo.save(company);
        return modelMapper.map(savedCompany, CompanyDto.class);
    }

    @Override
    public CompanyDto update(Long id, CompanyDto entity) {
        Company existingCompany = companyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Company.class.getSimpleName(), id));
        companyRepo.findByName(entity.getName())
                .filter(company -> !company.getId().equals(existingCompany.getId()))
                .ifPresent(user -> {
                    throw new DuplicateResourceException(Company.class.getSimpleName(), entity.getName());
                });

        modelMapper.map(entity, existingCompany);
        Company updatedCompany = companyRepo.save(existingCompany);
        return modelMapper.map(updatedCompany, CompanyDto.class);
    }

    @Override
    public CompanyDto findById(Long id) {
        Company existingCompany = companyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Company.class.getSimpleName(), id));
        return modelMapper.map(existingCompany, CompanyDto.class);
    }

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companies = companyRepo.findAll();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyDto.class))
                .toList();
    }

    @Override
    public List<CompanyDto> enable(List<Long> ids) {
        List<Company> companies = companyRepo.findAllById(ids);
        if (companies.isEmpty()) {
            throw new ResourceNotFoundException(Company.class.getSimpleName(), ids);
        }
        for (Company company : companies) {
            company.setEnabled(true);
        }
        List<Company> updatedUsers = companyRepo.saveAll(companies);
        return updatedUsers.stream()
                .map(company -> modelMapper.map(company, CompanyDto.class))
                .toList();
    }

    @Override
    public List<CompanyDto> disable(List<Long> ids) {
        List<Company> companies = companyRepo.findAllById(ids);
        if (companies.isEmpty()) {
            throw new ResourceNotFoundException(Company.class.getSimpleName(), ids);
        }
        for (Company company : companies) {
            company.setEnabled(false);
        }
        List<Company> updatedUsers = companyRepo.saveAll(companies);
        return updatedUsers.stream()
                .map(company -> modelMapper.map(company, CompanyDto.class))
                .toList();
    }

    @Override
    public List<CompanyDto> delete(List<Long> ids) {
        List<Company> companies = companyRepo.findAllById(ids);

        if (companies.isEmpty())
            throw new ResourceNotFoundException(Company.class.getSimpleName(), ids);

        List<CompanyDto> deletedCompanies = companies.stream()
                .map(company -> modelMapper.map(company, CompanyDto.class))
                .toList();

        companyRepo.deleteAll(companies);
        return deletedCompanies;
    }

    @Override
    public void deleteAll() {
        companyRepo.deleteAll();
    }
}
