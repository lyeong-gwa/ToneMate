package com.a603.tonemate.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_octavelow", columnList = "octaveLow"),@Index(name = "idx_octavehigh", columnList = "octaveHigh")})//index
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private Float mfccMean;
    private Float stftMean;
    private Float zcrMean;
    private Float spcMean;
    private Float sprMean;
    private Float rmsMean;
    private Float mfccVar;
    private Float stftVar;
    private Float zcrVar;
    private Float spcVar;
    private Float sprVar;
    private Float rmsVar;

    @Column
    private Integer octaveLow = 0;
    @Column
    private Integer octaveHigh = 500;

    //private Long singerId;
    private String title;
    private String numKy;
    private String numTj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singer_id")
    private Singer singer;
}
