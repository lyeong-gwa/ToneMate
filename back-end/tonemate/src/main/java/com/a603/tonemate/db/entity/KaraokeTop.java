package com.a603.tonemate.db.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class KaraokeTop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long KaraokeTopId;
    private int tjNum;
    private String tjTitle;
    private String tjSinger;
    private int kyNum;
    private String kyTitle;
    private String kySinger;
}
