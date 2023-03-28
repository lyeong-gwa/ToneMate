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
public class PitchAnalysisResp {
    @ApiModelProperty(value = "음역대 분석 결과 id")
    private Long pitchId;

    @ApiModelProperty(value = "사용자 음역대의 최저음")
    private int octave_low;

    @ApiModelProperty(value = "사용자 음역대의 최고음")
    private int octave_high;

    @ApiModelProperty(value = "음역대 분석 검사 일시")
    private LocalDateTime time;
}
