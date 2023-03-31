package com.a603.tonemate.db.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Karaoke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long karaokeId;
    private int kyNum;
    private int tjNum;
    private String title;
    private String singer;
}
