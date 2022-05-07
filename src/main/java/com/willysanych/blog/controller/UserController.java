package com.willysanych.blog.controller;

import com.willysanych.blog.dto.RolesRequest;
import com.willysanych.blog.model.User;
import com.willysanych.blog.service.AuthService;
import com.willysanych.blog.service.UserDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/update-roles")
    public ResponseEntity updateRoles(@RequestBody RolesRequest rolesRequest) {
        authService.updateRoles(rolesRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDetailService.getAllUsers(), HttpStatus.OK);
    }
}
