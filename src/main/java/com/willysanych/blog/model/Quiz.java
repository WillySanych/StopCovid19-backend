package com.willysanych.blog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    private String username;

    @Column
    private String firstQuestion;

    @Column
    private String secondQuestion;

    @Lob
    @Column
    private String thirdQuestion;

    @Column
    private String fourthQuestion;

    @Column
    private String fifthQuestion;

    @Column
    private String sixthQuestion;

    @Column
    private String seventhQuestion;

    @Column
    private String eighthQuestion;

    @Column
    private String ninthQuestion;

    @Column
    private String tenthQuestion;

    @Lob
    @Column
    private String eleventhQuestion;

    @Lob
    @Column
    private String twelfthQuestion;

    @Column
    private String thirteenthQuestion;

    @Lob
    @Column
    private String fourteenthQuestion;

    @Lob
    @Column
    private String fifteenthQuestion;

    @Column
    private Instant createdOn;

}
