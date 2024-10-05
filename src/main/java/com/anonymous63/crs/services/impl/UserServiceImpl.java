package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.exceptions.DuplicateResourceException;
import com.anonymous63.crs.exceptions.ResourceNotFoundException;
import com.anonymous63.crs.models.Role;
import com.anonymous63.crs.models.User;
import com.anonymous63.crs.repositories.RoleRepo;
import com.anonymous63.crs.repositories.UserRepo;
import com.anonymous63.crs.services.UserService;
import com.anonymous63.crs.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
    }

    public UserDto register(UserDto userDto) {
        this.userRepo.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });
        User user = this.modelMapper.map(userDto, User.class);

        String password = userDto.getPassword();
        user.setPassword(this.passwordEncoder.encode(password));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = this.roleRepo.findById(Constants.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "name", "USER"));
            user.getRoles().add(userRole);
        } else {
            for (Role role : user.getRoles()) {
                Role newRole = this.roleRepo.findById(role.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "id", role.getId()));
                user.getRoles().add(newRole);
            }
        }
        User registerUser = this.userRepo.save(user);
        return this.modelMapper.map(registerUser, UserDto.class);
    }

    @Override
    public UserDto save(UserDto entity) {
        if (entity.getId() != null || entity.getUsername() != null && entity.getUsername().isEmpty()) {
            this.userRepo.findById(entity.getId()).ifPresent(u -> {
                throw new DuplicateResourceException(User.class.getSimpleName(), "id", entity.getId());
            });
            this.userRepo.findByUsername(entity.getUsername()).ifPresent(u -> {
                throw new DuplicateResourceException(User.class.getSimpleName(), "username", entity.getUsername());
            });
        }

        User user = this.modelMapper.map(entity, User.class);
        if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(Constants.DEFAULT_PASSWORD + entity.getUsername()));
        } else {
            user.setPassword(this.passwordEncoder.encode(entity.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = this.roleRepo.findById(Constants.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "name", "USER"));
            user.getRoles().add(userRole);
        } else {
            for (Role role : user.getRoles()) {
                Role newRole = this.roleRepo.findById(role.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "id", role.getId()));
                user.getRoles().add(newRole);
            }
        }
        User saveUser = this.userRepo.save(user);
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDto update(UserDto entity) {
        return null;
    }

    @Override
    public UserDto findById(Long aLong) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return List.of();
    }
}
