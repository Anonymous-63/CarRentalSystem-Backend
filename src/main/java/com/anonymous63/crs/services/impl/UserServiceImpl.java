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
import com.anonymous63.crs.utils.Global;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;
    private final Global global;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepo roleRepo, Global global) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.global = global;
    }

    public UserDto register(UserDto userDto) {

        // Check if the username already exists
        userRepo.findByUsername(userDto.getUsername()).ifPresent(user -> {
            throw new DuplicateResourceException(User.class.getSimpleName(), userDto.getUsername());
        });

        // Map DTO to Entity
        User user = modelMapper.map(userDto, User.class);

        // Encode the password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set user roles
        Set<Role> roles = (userDto.getRoles() == null || userDto.getRoles().isEmpty()) ?
                Collections.singleton(roleRepo.findById(Constants.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), Constants.ROLE_USER))) :
                userDto.getRoles().stream()
                        .map(role -> roleRepo.findById(role.getId())
                                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), role.getId())))
                        .collect(Collectors.toSet());

        user.setRoles(roles);

        // Save and return the registered user
        User registerUser = userRepo.save(user);
        return modelMapper.map(registerUser, UserDto.class);
    }


    @Override
    public UserDto save(UserDto userDto) {
        // Check for duplicate username
        userRepo.findByUsername(userDto.getUsername())
                .ifPresent(user -> {
                    throw new DuplicateResourceException(User.class.getSimpleName(), userDto.getUsername());
                });

        // Check for duplicate ID (if present)
        if (userDto.getId() != null) {
            userRepo.findById(userDto.getId())
                    .ifPresent(user -> {
                        throw new DuplicateResourceException(User.class.getSimpleName(), userDto.getId());
                    });
        }

        userRepo.findByPhoneNoOrEmailId(userDto.getPhoneNo(), userDto.getEmailId())
                .ifPresent(user -> {
                    throw new DuplicateResourceException(User.class.getSimpleName(), user.getPhoneNo().equals(userDto.getPhoneNo()) ? userDto.getPhoneNo() : userDto.getEmailId());
                });

        // Map UserDto to User entity
        User user = modelMapper.map(userDto, User.class);

        // Set password: default if empty, else use provided
        String password = (userDto.getPassword() == null || userDto.getPassword().isEmpty()) ?
                Constants.DEFAULT_PASSWORD + userDto.getUsername() : userDto.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        // Set roles: default to ROLE_USER if none provided
        Set<Role> roles = (userDto.getRoles() == null || userDto.getRoles().isEmpty()) ?
                Collections.singleton(roleRepo.findById(Constants.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), Constants.ROLE_USER))) :
                userDto.getRoles().stream()
                        .map(role -> roleRepo.findById(role.getId())
                                .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), role.getId())))
                        .collect(Collectors.toSet());
        user.setRoles(roles);

        // Save user and map back to UserDto
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        // Check if the user exists
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), id));

        // Check for duplicate username (excluding the current user)
        userRepo.findByUsername(userDto.getUsername())
                .filter(user -> !user.getId().equals(existingUser.getId()))
                .ifPresent(user -> {
                    throw new DuplicateResourceException(User.class.getSimpleName(), userDto.getUsername());
                });

        // Check for duplicate phone number or email (excluding the current user)
        userRepo.findByPhoneNoOrEmailId(userDto.getPhoneNo(), userDto.getEmailId())
                .filter(user -> !user.getId().equals(existingUser.getId()))
                .ifPresent(user -> {
                    throw new DuplicateResourceException(User.class.getSimpleName(),
                            user.getPhoneNo().equals(userDto.getPhoneNo()) ? userDto.getPhoneNo() : userDto.getEmailId());
                });

        // Map UserDto to existing User entity (ensure ID remains unchanged)
        modelMapper.map(userDto, existingUser);

        // Handle password: do not update if empty, else encode and set
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Set roles: update roles while keeping existing ones if none provided
        Set<Role> roles = (userDto.getRoles() == null || userDto.getRoles().isEmpty()) ?
                existingUser.getRoles() : userDto.getRoles().stream()
                .map(role -> roleRepo.findById(role.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), role.getId())))
                .collect(Collectors.toSet());
        existingUser.setRoles(roles);

        // Save updated user and map back to UserDto
        User updatedUser = userRepo.save(existingUser); // This should not alter the ID
        return modelMapper.map(updatedUser, UserDto.class);
    }


    @Override
    public UserDto findById(Long id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), id));
        return modelMapper.map(existingUser, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    @Transactional
    public List<UserDto> enable(List<Long> ids) {
        List<User> users = userRepo.findAllById(ids);

        if (users.isEmpty())
            throw new ResourceNotFoundException(User.class.getSimpleName(), ids);

        for (User user : users)
            user.setEnabled(true);

        List<User> updatedUsers = userRepo.saveAll(users);
        return updatedUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> disable(List<Long> ids) {
        List<User> users = userRepo.findAllById(ids);

        if (users.isEmpty())
            throw new ResourceNotFoundException(User.class.getSimpleName(), ids);

        for (User user : users)
            user.setEnabled(false);

        List<User> updatedUsers = userRepo.saveAll(users);
        return updatedUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public List<UserDto> delete(List<Long> ids) {
        List<User> users = userRepo.findAllById(ids);

        if (users.isEmpty())
            throw new ResourceNotFoundException(User.class.getSimpleName(), ids);

        List<UserDto> deletedUsers = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();

        userRepo.deleteAll(users);
        return deletedUsers;
    }

    @Override
    public void deleteAll() {
        userRepo.deleteAll();
    }

}
