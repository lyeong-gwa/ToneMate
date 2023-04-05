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
    @Column(name = "timbre_id")
    private Long timbreId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "singer_id")
    private Singer singer;
    private Float similarityPercent;

    public SingerSimilarity(Long timbreId, Singer singer, Float similarityPercent) {
        this.timbreId = timbreId;
        this.singer = singer;
        this.similarityPercent = similarityPercent;
    }
}
