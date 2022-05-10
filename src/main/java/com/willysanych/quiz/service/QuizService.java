package com.willysanych.quiz.service;

import com.willysanych.quiz.dto.QuizDto;
import com.willysanych.quiz.exception.QuizNotFoundException;
import com.willysanych.quiz.model.Quiz;
import com.willysanych.quiz.model.User;
import com.willysanych.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class QuizService {

    @Autowired
    private AuthService authService;

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
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
        quizDto.setFirstQuestion(quiz.getFirstQuestion());
        quizDto.setSecondQuestion(quiz.getSecondQuestion());
        quizDto.setThirdQuestion(quiz.getThirdQuestion());
        quizDto.setFourthQuestion(quiz.getFourthQuestion());
        quizDto.setFifthQuestion(quiz.getFifthQuestion());
        quizDto.setSixthQuestion(quiz.getSixthQuestion());
        quizDto.setSeventhQuestion(quiz.getSeventhQuestion());
        quizDto.setEighthQuestion(quiz.getEighthQuestion());
        quizDto.setNinthQuestion(quiz.getNinthQuestion());
        quizDto.setTenthQuestion(quiz.getTenthQuestion());
        quizDto.setEleventhQuestion(quiz.getEleventhQuestion());
        quizDto.setTwelfthQuestion(quiz.getTwelfthQuestion());
        quizDto.setThirteenthQuestion(quiz.getThirteenthQuestion());
        quizDto.setFourteenthQuestion(quiz.getFourteenthQuestion());
        quizDto.setFifteenthQuestion(quiz.getFifteenthQuestion());
        quizDto.setUsername(quiz.getUsername());
        quizDto.setCreatedOn(quiz.getCreatedOn());
        return quizDto;
    }

    @Async
    Quiz mapFromDtoToQuiz(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setFirstQuestion(quizDto.getFirstQuestion());
        quiz.setSecondQuestion(quizDto.getSecondQuestion());
        quiz.setThirdQuestion(quizDto.getThirdQuestion());
        quiz.setFourthQuestion(quizDto.getFourthQuestion());
        quiz.setFifthQuestion(quizDto.getFifthQuestion());
        quiz.setSixthQuestion(quizDto.getSixthQuestion());
        quiz.setSeventhQuestion(quizDto.getSeventhQuestion());
        quiz.setEighthQuestion(quizDto.getEighthQuestion());
        quiz.setNinthQuestion(quizDto.getNinthQuestion());
        quiz.setTenthQuestion(quizDto.getTenthQuestion());
        quiz.setEleventhQuestion(quizDto.getEleventhQuestion());
        quiz.setTwelfthQuestion(quizDto.getTwelfthQuestion());
        quiz.setThirteenthQuestion(quizDto.getThirteenthQuestion());
        quiz.setFourteenthQuestion(quizDto.getFourteenthQuestion());
        quiz.setFifteenthQuestion(quizDto.getFifteenthQuestion());
        User loggedInUser = authService.getCurrentUser().orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        quiz.setCreatedOn(Instant.now());
        quiz.setUsername(loggedInUser.getUsername());
        return quiz;
    }
}
