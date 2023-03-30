package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PitchAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitchId;
    private Long userId;

    private int octaveLow;
    private int octaveHigh;

    @CreatedDate
    private LocalDateTime time;

    @Builder
	public PitchAnalysis(Long pitchId, Long userId, int octaveLow, int octaveHigh, LocalDateTime time) {
		super();
		this.pitchId = pitchId;
		this.userId = userId;
		this.octaveLow = octaveLow;
		this.octaveHigh = octaveHigh;
		this.time = time;
	}
}
