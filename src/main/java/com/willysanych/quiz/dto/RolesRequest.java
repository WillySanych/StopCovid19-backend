package com.willysanych.quiz.dto;

import com.willysanych.quiz.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RolesRequest {
    String username;
    Set<Role> roles;
}
