package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PitchAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitchId;
    private Long userId;

    private int octave_low;
    private int octave_high;

    @CreatedDate
    private LocalDateTime time;

    @Builder
	public PitchAnalysis(Long pitchId, Long userId, int octave_low, int octave_high, LocalDateTime time) {
		super();
		this.pitchId = pitchId;
		this.userId = userId;
		this.octave_low = octave_low;
		this.octave_high = octave_high;
		this.time = time;
	}
}
