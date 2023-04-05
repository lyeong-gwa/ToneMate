package com.a603.tonemate.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class TimbreAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timbreId;
    private Long userId;

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

    private Long singer1;
    private Long singer2;
    private Long singer3;
    private Long singer4;
    private Long singer5;

    private float similarity1;
    private float similarity2;
    private float similarity3;
    private float similarity4;
    private float similarity5;

    @CreatedDate
    private LocalDateTime time;

    @Builder
    public TimbreAnalysis(Long timbreId, Long userId, float mfccMean, float stftMean, float zcrMean, float spcMean, float sprMean, float rmsMean,
                          float mfccVar, float stftVar, float zcrVar, float spcVar, float sprVar, float rmsVar,
                          Long singer1, Long singer2, Long singer3, Long singer4, Long singer5,
                          float similarity1, float similarity2, float similarity3, float similarity4, float similarity5, LocalDateTime time) {
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
