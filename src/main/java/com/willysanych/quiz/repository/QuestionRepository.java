package com.willysanych.quiz.repository;

import com.willysanych.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select questionText from Question")
    List<String> findAllQuestionsText();
}
