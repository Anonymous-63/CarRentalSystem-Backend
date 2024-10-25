package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.payloads.response.APIResponse;
import com.anonymous63.crs.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> getUser(@PathVariable Long id){
        UserDto retrievedUser = this.userService.findById(id);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User fetched successfully")
                .results(retrievedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<APIResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> allUsers = this.userService.findAll();
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users fetched successfully")
                .results(allUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse<UserDto>> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = this.userService.save(userDto);

        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User created successfully")
                .results(createdUser)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);  // Explicitly set HTTP status to 201 (Created)
    }


    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = this.userService.update(id, userDto);
        APIResponse<UserDto> response = APIResponse.<UserDto>builder()
                .status(true)
                .message("User updated successfully")
                .results(updatedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("enable")
    public ResponseEntity<APIResponse<List<UserDto>>> enableUser(@RequestBody List<Long> ids){
        List<UserDto> enabledUsers = this.userService.enable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users enabled successfully")
                .results(enabledUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/disable")
    public ResponseEntity<APIResponse<List<UserDto>>> disableUser(@RequestBody List<Long> ids){
        List<UserDto> disabledUser = this.userService.disable(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users disabled successfully")
                .results(disabledUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse<List<UserDto>>> deleteUser(@RequestBody List<Long> ids){
        List<UserDto> deletedUser = this.userService.delete(ids);
        APIResponse<List<UserDto>> response = APIResponse.<List<UserDto>>builder()
                .status(true)
                .message("Users deleted successfully")
                .results(deletedUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<APIResponse<String>> deleteAllUser(){
        this.userService.deleteAll();
        APIResponse<String> response = APIResponse.<String>builder()
                .status(true)
                .message("All users deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
