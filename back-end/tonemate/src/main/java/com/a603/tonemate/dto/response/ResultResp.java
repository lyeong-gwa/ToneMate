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
@ApiModel(value = "음색/음역대 분석 검사 결과 리스트", description = "음색/음역대 검사 결과 목록의 정보들이 담김(검사 id, 검사 유형, 검사 일시)")
public class ResultResp implements Comparable<ResultResp> {

    @ApiModelProperty(value = "검사 결과 id")
    private Long resultId;

    @ApiModelProperty(value = "검사 결과 유형(음색 or 음역대)")
    private String type;

    @ApiModelProperty(value = "검사 일시")
    private LocalDateTime time;

    @Override
    public int compareTo(ResultResp o) {
        return o.getTime().compareTo(time);
    }
}
