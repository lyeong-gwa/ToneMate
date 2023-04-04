package com.a603.tonemate.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@NoArgsConstructor
public class KaraokeDto extends KaraokeCommonDto {


    @ApiModelProperty(value = "애창곡 존재 여부")
    private Boolean isLike;

    @QueryProjection
    public KaraokeDto(Integer tjNum, String singer, String title, Boolean isLike) {
        super(tjNum, singer, title);
        this.isLike = isLike;
    }
}