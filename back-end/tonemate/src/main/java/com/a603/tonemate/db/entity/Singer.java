package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a603.tonemate.enumpack.Genre;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_genre", columnList = "genre")})//index
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singerId;
    private String name;
    @Column(nullable = true)
    private Boolean gender;
    private Date birthYear;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) default 'UNKNOW'")
    private Genre genre;

   
    
    @Builder
    public Singer(Long singerId, String name, Boolean gender, Date birthYear, Genre genre) {
        this.singerId = singerId;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.genre = genre;
    }



}
