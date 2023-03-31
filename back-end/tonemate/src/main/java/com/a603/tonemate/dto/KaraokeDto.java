package com.a603.tonemate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KaraokeDto {
    @ApiModelProperty(value = "tj 노래방 번호")
    private int tjNum;
    @ApiModelProperty(value = "금영 노래방 번호")
    private int kyNum;

    @ApiModelProperty(value = "가수 이름")
    private String singer;

    @ApiModelProperty(value = "노래 제목")
    private String title;

    @Builder
    public KaraokeDto(int tjNum, int kyNum, String singer, String title) {
        this.tjNum = tjNum;
        this.kyNum = kyNum;
        this.singer = singer;
        this.title = title;
    }
}
