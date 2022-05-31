package com.willysanych.quiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class QuizDto {

    private Long id;

    private String username;

    private Instant createdOn;

    private List<String> answers;

}
