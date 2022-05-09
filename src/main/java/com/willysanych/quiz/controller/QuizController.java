package com.willysanych.quiz.controller;

import com.willysanych.quiz.dto.QuizDto;
import com.willysanych.quiz.model.Quiz;
import com.willysanych.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public Page<Quiz> showAllQuizzes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return quizService.showAllQuizzes(paging);
    }

//    @PreAuthorize("hasAuthority('DOCTOR')")
//    @GetMapping("/all")
//    public ResponseEntity<List<QuizDto>> showAllQuizzes() {
//        return new ResponseEntity<>(quizService.showAllQuizzes(), HttpStatus.OK);
//    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/get/{id}")
    public ResponseEntity<QuizDto> getSingleQuiz(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(quizService.readSingleQuiz(id), HttpStatus.OK);
    }
}
