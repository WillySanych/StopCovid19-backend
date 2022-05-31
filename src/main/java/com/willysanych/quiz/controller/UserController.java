package com.willysanych.quiz.controller;

import com.willysanych.quiz.dto.RolesRequest;
import com.willysanych.quiz.model.User;
import com.willysanych.quiz.service.AuthService;
import com.willysanych.quiz.service.UserDetailServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;

    private final UserDetailServiceImpl userDetailService;

    public UserController(AuthService authService, UserDetailServiceImpl userDetailService) {
        this.authService = authService;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/update-roles")
    public ResponseEntity updateRoles(@RequestBody RolesRequest rolesRequest) {
        authService.updateRoles(rolesRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUsers")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return userDetailService.getAllUsers(paging);
    }

}
