package com.willysanych.quiz.service;

import com.willysanych.quiz.model.Question;
import com.willysanych.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;


    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public List<String> findAllQuestionsText() {
        return questionRepository.findAllQuestionsText();
    }
}
