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
public class PitchAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitchId;
    private Long userId;
    private String time;
    private String octave_low;
    private String octave_high;

    @Builder
	public PitchAnalysis(Long pitchId, Long userId, String time, String octave_low, String octave_high) {
		super();
		this.pitchId = pitchId;
		this.userId = userId;
		this.time = time;
		this.octave_low = octave_low;
		this.octave_high = octave_high;
	}



}
