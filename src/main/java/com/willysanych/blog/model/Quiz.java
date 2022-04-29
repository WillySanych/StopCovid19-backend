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

    @Lob
    @Column
    private String firstQuestion;

    @Lob
    @Column
    private String secondQuestion;

    @Lob
    @Column
    private String thirdQuestion;

    @Lob
    @Column
    private String fourthQuestion;

    @Lob
    @Column
    private String fifthQuestion;

    @Lob
    @Column
    private String sixthQuestion;

    @Lob
    @Column
    private String seventhQuestion;

    @Lob
    @Column
    private String eighthQuestion;

    @Lob
    @Column
    private String ninthQuestion;

    @Lob
    @Column
    private String tenthQuestion;

    @Lob
    @Column
    private String eleventhQuestion;

    @Lob
    @Column
    private String twelfthQuestion;

    @Lob
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
