package com.anonymous63.crs.controllers;

import com.anonymous63.crs.dtos.UserDto;
import com.anonymous63.crs.payloads.request.JwtAuthRequest;
import com.anonymous63.crs.payloads.response.JwtAuthResponse;
import com.anonymous63.crs.services.UserService;
import com.anonymous63.crs.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = this.jwtUtil.generateToken(userDetails);
        String refreshToken = this.jwtUtil.generateRefreshToken(userDetails);

        JwtAuthResponse jwtAuthResponse = JwtAuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto newUser = this.userService.register(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
