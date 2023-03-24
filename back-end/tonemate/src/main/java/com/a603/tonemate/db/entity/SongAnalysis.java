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
public class SongAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
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
    private String octaveLow;
    private String octaveHigh;


    @Builder
    public SongAnalysis(Long songId, String mfccMean, String stftMean, String zcrMean, String spcMean, String sprMean, String rmsMean,
                        String mfccVar, String stftVar, String zcrVar, String spcVar, String sprVar, String rmsVar, String octaveLow, String octaveHigh) {
        this.songId = songId;
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
        this.octaveLow = octaveLow;
        this.octaveHigh = octaveHigh;
    }


    public void updateOctaveLow(String octaveLow) {
        this.octaveLow = octaveLow;
    }

    public void updateOctaveHigh(String octaveHigh) {
        this.octaveHigh = octaveHigh;
    }

}
