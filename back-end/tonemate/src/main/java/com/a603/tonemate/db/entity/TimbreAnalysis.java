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
public class TimbreAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timbreId;
    private Long userId;

    private String mfccMean;
    private String stftMean;
    private String zcrMean;
    private String spcMean;
    private String sprMean;
    private String rmsMean;
    private String mfccVar;
    private String stftVar;
    private String zcrVar;
    private String spcVar;
    private String sprVar;
    private String rmsVar;

    private String singer1;
    private String singer2;
    private String singer3;
    private String singer4;
    private String singer5;

    private String similarity1;
    private String similarity2;
    private String similarity3;
    private String similarity4;
    private String similarity5;

    private String time;


    @Builder
    public TimbreAnalysis(Long timbreId, Long userId, String mfccMean, String stftMean, String zcrMean, String spcMean, String sprMean, String rmsMean,
                          String mfccVar, String stftVar, String zcrVar, String spcVar, String sprVar, String rmsVar,
                          String singer1, String singer2, String singer3, String singer4, String singer5,
                          String similarity1, String similarity2, String similarity3, String similarity4, String similarity5, String time) {
        this.timbreId = timbreId;
        this.userId = userId;

        this.mfccMean = mfccMean;
        this.stftMean = stftMean;
        this.zcrMean = zcrMean;
        this.spcMean = spcMean;
        this.sprMean = sprMean;
        this.rmsMean = rmsMean;
        this.mfccVar = mfccVar;
        this.stftVar = stftVar;
        this.zcrVar = zcrVar;
        this.spcVar = spcVar;
        this.sprVar = sprVar;
        this.rmsVar = rmsVar;

        this.singer1 = singer1;
        this.singer2 = singer2;
        this.singer3 = singer3;
        this.singer4 = singer4;
        this.singer5 = singer5;

        this.similarity1 = similarity1;
        this.similarity2 = similarity2;
        this.similarity3 = similarity3;
        this.similarity4 = similarity4;
        this.similarity5 = similarity5;

        this.time = time;
    }
}
