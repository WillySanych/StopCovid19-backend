package com.willysanych.quiz.controller;

import com.willysanych.quiz.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity getAllQuestions() {
        return new ResponseEntity<>(questionService.findAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/all-texts")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity getAllQuestionsText() {
        return new ResponseEntity<>(questionService.findAllQuestionsText(), HttpStatus.OK);
    }
}
