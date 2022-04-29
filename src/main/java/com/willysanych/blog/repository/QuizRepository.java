package com.willysanych.blog.repository;

import com.willysanych.blog.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository <Quiz, Long> {
}
