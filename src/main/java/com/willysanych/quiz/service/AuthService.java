package com.willysanych.quiz.service;

import com.willysanych.quiz.dto.*;
import com.willysanych.quiz.model.Role;
import com.willysanych.quiz.model.User;
import com.willysanych.quiz.repository.UserRepository;
import com.willysanych.quiz.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(Role.USER);
        defaultRoles.add(Role.PATIENT);
        user.setRoles(defaultRoles);
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()
                -> new UsernameNotFoundException("No user found " + loginRequest.getUsername()));
        return AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .roles(user.getRoles())
                .build();

    }

    public Optional<User> getCurrentUser() {
        User principal = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return Optional.of(principal);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        User user = userRepository.findByUsername(refreshTokenRequest.getUsername()).orElseThrow(()
                -> new UsernameNotFoundException("No user found " + refreshTokenRequest.getUsername()));
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public void updateRoles(RolesRequest rolesRequest) {

        String username = rolesRequest.getUsername();
        Set<Role> roles = rolesRequest.getRoles();
        System.out.println(roles);

        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("No user found " + username));
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        userRepository.save(user);

    }
}
