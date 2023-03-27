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
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private float mfccMean;
    private float stftMean;
    private float zcrMean;
    private float spcMean;
    private float sprMean;
    private float rmsMean;
    private float mfccVar;
    private float stftVar;
    private float zcrVar;
    private float spcVar;
    private float sprVar;
    private float rmsVar;
    private float octaveLow;
    private float octaveHigh;

    private Long singerId;
    private String title;
    private String num_ky;
    private String num_tj;

    @Builder
	public Song(Long songId, float mfccMean, float stftMean, float zcrMean, float spcMean, float sprMean,
			float rmsMean, float mfccVar, float stftVar, float zcrVar, float spcVar, float sprVar, float rmsVar,
			float octaveLow, float octaveHigh, Long singerId, String title, String num_ky, String num_tj) {
		super();
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
		this.singerId = singerId;
		this.title = title;
		this.num_ky = num_ky;
		this.num_tj = num_tj;
	}



    public void updateOctaveLow(float octaveLow) {
        this.octaveLow = octaveLow;
    }

    public void updateOctaveHigh(float octaveHigh) {
        this.octaveHigh = octaveHigh;
    }


}
