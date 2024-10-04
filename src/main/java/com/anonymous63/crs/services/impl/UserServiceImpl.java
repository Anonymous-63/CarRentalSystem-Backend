package com.anonymous63.crs.services.impl;

import com.anonymous63.crs.dtos.UserDto;
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
        User user = this.modelMapper.map(userDto, User.class);
        this.userRepo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });

        String password = userDto.getPassword();
        user.setPassword(this.passwordEncoder.encode(password));

        Role role = this.roleRepo.findById(Constants.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "id", Constants.ROLE_USER));
        user.getRoles().add(role);
        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }
}
