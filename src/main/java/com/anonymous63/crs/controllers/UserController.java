package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.payloads.response.APIResponse;
import com.anonymous63.crs.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController implements CrudController<UserDto, Long> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> get(Long id) {
        UserDto retrievedUser = this.userService.findById(id);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User fetched successfully")
                .results(retrievedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<UserDto>>> getAll() {
        List<UserDto> allUsers = this.userService.findAll();
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users fetched successfully")
                .results(allUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> create(UserDto entity) {
        UserDto createdUser = this.userService.save(entity);

        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User created successfully")
                .results(createdUser)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<UserDto>> update(Long id, UserDto entity) {
        UserDto updatedUser = this.userService.update(id, entity);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User updated successfully")
                .results(updatedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<APIResponse<List<UserDto>>> enable(List<Long> ids) {
        List<UserDto> enabledUsers = this.userService.enable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users enabled successfully")
                .results(enabledUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<UserDto>>> disable(List<Long> ids) {
        List<UserDto> disabledUser = this.userService.disable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users disabled successfully")
                .results(disabledUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<List<UserDto>>> delete(List<Long> ids) {
        List<UserDto> deletedUser = this.userService.delete(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users deleted successfully")
                .results(deletedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse<String>> deleteAll() {
        this.userService.deleteAll();
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("All users deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
