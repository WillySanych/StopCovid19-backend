package com.willysanych.blog.dto;

import com.willysanych.blog.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RolesRequest {
    String username;
    Set<Role> roles;
}
