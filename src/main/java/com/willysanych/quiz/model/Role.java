package com.willysanych.quiz.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, PATIENT, DOCTOR, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
