package com.a603.tonemate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "음색 분석 검사 결과", description = "사용자가 보낸 wav 파일에 대한 음색 분석 결과 정보들이 담김")
public class TimbreAnalysisResp {
    @ApiModelProperty(value = "음색 분석 결과 id")
    private Long timbreId;

    @ApiModelProperty(value = "음악 특성 값들")
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

    @ApiModelProperty(value = "유사도가 높은 상위 5명의 가수 이름")
    private String singer1;
    private String singer2;
    private String singer3;
    private String singer4;
    private String singer5;

    @ApiModelProperty(value = "상위 5명의 각 가수와의 유사도")
    private float similarity1;
    private float similarity2;
    private float similarity3;
    private float similarity4;
    private float similarity5;

    @ApiModelProperty(value = "음색 분석 검사 일시")
    private LocalDateTime time;
}
