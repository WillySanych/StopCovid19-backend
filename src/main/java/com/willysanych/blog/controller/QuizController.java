package com.willysanych.blog.controller;

import com.willysanych.blog.dto.QuizDto;
import com.willysanych.blog.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping
    public ResponseEntity createQuiz(@RequestBody QuizDto quizDto) {
        quizService.createQuiz(quizDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/all")
    public ResponseEntity<List<QuizDto>> showAllQuizzes() {
        return new ResponseEntity<>(quizService.showAllQuizzes(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/get/{id}")
    public ResponseEntity<QuizDto> getSingleQuiz(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(quizService.readSingleQuiz(id), HttpStatus.OK);
    }
}
