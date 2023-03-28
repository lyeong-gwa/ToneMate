package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.a603.tonemate.enumpack.Genre;

@Entity
@Getter
@NoArgsConstructor
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singerId;
    private String name;
    @Column(nullable = true)
    private boolean gender;
    private Date birthYear;
    
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Builder
    public Singer(Long singerId, String name, boolean gender, Date birthYear, Genre genre) {
        this.singerId = singerId;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.genre = genre;
    }



}
