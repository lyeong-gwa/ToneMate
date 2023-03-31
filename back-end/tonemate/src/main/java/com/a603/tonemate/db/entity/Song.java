package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    
    @Column
    private int octaveLow = 0;
    @Column
    private int octaveHigh = 500;

    private Long singerId;
    private String title;
    private String numKy;
    private String numTj;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "singerId", referencedColumnName = "singerId",insertable = false, updatable = false)
    private Singer singer;
    
    
    @Builder
	public Song(Long songId, float mfccMean, float stftMean, float zcrMean, float spcMean, float sprMean,
			float rmsMean, float mfccVar, float stftVar, float zcrVar, float spcVar, float sprVar, float rmsVar,
			int octaveLow, int octaveHigh, Long singerId, String title, String numKy, String numTj) {
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
		this.numKy = numKy;
		this.numTj = numTj;
	}



    public void updateOctaveLow(int octaveLow) {
        this.octaveLow = octaveLow;
    }

    public void updateOctaveHigh(int octaveHigh) {
        this.octaveHigh = octaveHigh;
    }


}
