package com.willysanych.quiz.repository;

import com.willysanych.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
//    Page<Quiz> findAll(Pageable pageable);
}
