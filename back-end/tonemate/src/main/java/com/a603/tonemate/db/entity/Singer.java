package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singerId;
    private String name;
    private String gender;
    private String birthYear;
    private String genre;

    @Builder
    public Singer(Long singerId, String name, String gender, String birthYear, String genre) {
        this.singerId = singerId;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.genre = genre;
    }



}
