package com.a603.tonemate.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SingerSimilarity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SingerSimilarityId;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    private Singer singer;
    private Float similarityPercent;

    public SingerSimilarity(Singer singer, Float similarityPercent) {
        this.singer = singer;
        this.similarityPercent = similarityPercent;
    }
}
