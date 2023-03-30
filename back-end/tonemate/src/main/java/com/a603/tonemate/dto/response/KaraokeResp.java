package com.a603.tonemate.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@Builder
public class KaraokeResp {
    @ApiModelProperty(value = "음악 순위")
    private Long KaraokeId;
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

}
