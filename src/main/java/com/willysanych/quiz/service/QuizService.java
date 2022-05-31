package com.willysanych.quiz.service;

import com.willysanych.quiz.dto.QuizDto;
import com.willysanych.quiz.exception.QuizNotFoundException;
import com.willysanych.quiz.model.Quiz;
import com.willysanych.quiz.model.User;
import com.willysanych.quiz.repository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class QuizService {

    private final AuthService authService;

    private final QuizRepository quizRepository;

    public QuizService(AuthService authService, QuizRepository quizRepository) {
        this.authService = authService;
        this.quizRepository = quizRepository;
    }

    public Page<Quiz> showAllQuizzes(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    @Transactional
    public void createQuiz(QuizDto quizDto) {
        Quiz quiz = mapFromDtoToQuiz(quizDto);
        quizRepository.save(quiz);
    }

    @Transactional
    public QuizDto readSingleQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(
                () -> new QuizNotFoundException("Post with id: " + id + " not found"));
        return mapFromQuizToDto(quiz);
    }

    @Async
    QuizDto mapFromQuizToDto(Quiz quiz) {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(quiz.getId());
        quizDto.setAnswers(quiz.getAnswers());
        quizDto.setUsername(quiz.getUsername());
        quizDto.setCreatedOn(quiz.getCreatedOn());
        return quizDto;
    }

    @Async
    Quiz mapFromDtoToQuiz(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setAnswers(quizDto.getAnswers());
        User loggedInUser = authService.getCurrentUser().orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        quiz.setCreatedOn(Instant.now());
        quiz.setUsername(loggedInUser.getUsername());
        return quiz;
    }
}
