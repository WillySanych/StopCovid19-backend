package com.willysanych.quiz.service;

import com.willysanych.quiz.dto.*;
import com.willysanych.quiz.model.Role;
import com.willysanych.quiz.model.User;
import com.willysanych.quiz.repository.UserRepository;
import com.willysanych.quiz.security.JwtProvider;
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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

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

    @Transactional
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

    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Transactional
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
