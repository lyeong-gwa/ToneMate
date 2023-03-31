package com.a603.tonemate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class KaraokeTopDto {
    @ApiModelProperty(value = "음악 순위")
    private Long karaokeTopId;
    @ApiModelProperty(value = "tj 노래방 번호")
    private int tjNum;
    @ApiModelProperty(value = "tj 노래 제목")
    private String tjTitle;
    @ApiModelProperty(value = "tj 가수 이름")
    private String tjSinger;
    @ApiModelProperty(value = "금영 노래방 번호")
    private int kyNum;
    @ApiModelProperty(value = "금영 노래 제목")
    private String kyTitle;
    @ApiModelProperty(value = "금영 가수 이름")
    private String kySinger;

    @Builder
    public KaraokeTopDto(Long karaokeTopId, int tjNum, String tjTitle, String tjSinger, int kyNum, String kyTitle, String kySinger) {
        this.karaokeTopId = karaokeTopId;
        this.tjNum = tjNum;
        this.tjTitle = tjTitle;
        this.tjSinger = tjSinger;
        this.kyNum = kyNum;
        this.kyTitle = kyTitle;
        this.kySinger = kySinger;
    }
}
