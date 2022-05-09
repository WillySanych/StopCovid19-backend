package com.willysanych.quiz.dto;

import com.willysanych.quiz.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
    private Set<Role> roles;
}
