package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.CompanyDto;
import com.anonymous63.crs.dtos.ModelDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Company;
import com.anonymous63.crs.models.Model;
import com.anonymous63.crs.repositories.CompanyRepo;
import com.anonymous63.crs.repositories.ModelRepo;
import com.anonymous63.crs.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepo modelRepo;
    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepo modelRepo, CompanyRepo companyRepo, ModelMapper modelMapper) {
        this.modelRepo = modelRepo;
        this.companyRepo = companyRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelDto save(ModelDto entity) {
        modelRepo.findByName(entity.getName())
                .ifPresent(model -> {
                    throw new DuplicateResourceException(Model.class.getSimpleName(), entity.getName());
                });

        if (entity.getId() != null) {
            modelRepo.findById(entity.getId())
                    .ifPresent(model -> {
                        throw new DuplicateResourceException(Model.class.getSimpleName(), entity.getId());
                    });
        }

        // Map UserDto to User entity
        Model model = modelMapper.map(entity, Model.class);

        Company company = companyRepo.findById(model.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException(Company.class.getSimpleName(), entity.getId()));
        if (!company.getEnabled()) {
            throw new ResourceNotFoundException(Company.class.getSimpleName(), model.getCompany().getId());
        }
        model.setCompany(company);

        Model savedModel = modelRepo.save(model);
        ModelDto modelDto = modelMapper.map(savedModel, ModelDto.class);
        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
        modelDto.setCompany(companyDto);
        return modelDto;
    }

    @Override
    public ModelDto update(Long id, ModelDto entity) {
        Model existingModel = modelRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Model.class.getSimpleName(), id));
        modelRepo.findByName(entity.getName())
                .filter(model -> !model.getId().equals(existingModel.getId()))
                .ifPresent(user -> {
                    throw new DuplicateResourceException(Model.class.getSimpleName(), entity.getName());
                });

        modelMapper.map(entity, existingModel);

        Company company = companyRepo.findById(existingModel.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException(Company.class.getSimpleName(), entity.getId()));
        if (!company.getEnabled()) {
            throw new ResourceNotFoundException(Company.class.getSimpleName(), entity.getCompany().getId());
        }
        existingModel.setCompany(company);

        Model updatedModel = modelRepo.save(existingModel);
        ModelDto modelDto = modelMapper.map(updatedModel, ModelDto.class);
        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
        modelDto.setCompany(companyDto);
        return modelDto;
    }

    @Override
    public ModelDto findById(Long id) {
        Model existingModel = modelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Model.class.getSimpleName(), id));
        return modelMapper.map(existingModel, ModelDto.class);
    }

    @Override
    public List<ModelDto> findAll() {
        List<Model> models = modelRepo.findAll();
        return models.stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .toList();
    }

    @Override
    public List<ModelDto> enable(List<Long> ids) {
        List<Model> models = modelRepo.findAllById(ids);
        if (models.isEmpty()) {
            throw new ResourceNotFoundException(Model.class.getSimpleName(), ids);
        }
        for (Model model : models) {
            model.setEnabled(true);
        }
        List<Model> updatedModels = modelRepo.saveAll(models);
        return updatedModels.stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .toList();
    }

    @Override
    public List<ModelDto> disable(List<Long> ids) {
        List<Model> models = modelRepo.findAllById(ids);
        if (models.isEmpty()) {
            throw new ResourceNotFoundException(Model.class.getSimpleName(), ids);
        }
        for (Model model : models) {
            model.setEnabled(false);
        }
        List<Model> updatedModels = modelRepo.saveAll(models);
        return updatedModels.stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .toList();
    }

    @Override
    public List<ModelDto> delete(List<Long> ids) {
        List<Model> models = modelRepo.findAllById(ids);

        if (models.isEmpty())
            throw new ResourceNotFoundException(Model.class.getSimpleName(), ids);

        List<ModelDto> deletedModels = models.stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .toList();

        modelRepo.deleteAll(models);
        return deletedModels;
    }

    @Override
    public void deleteAll() {
        modelRepo.deleteAll();
    }
}
