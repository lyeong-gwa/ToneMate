package com.a603.tonemate.dto.response;

import com.a603.tonemate.dto.KaraokeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class KaraokeResp {

    @ApiModelProperty(value = "노래 정보들")
    private List<KaraokeDto> songs;

    @ApiModelProperty(value = "한 페이지에서 나타내는 노래의 수")
    private int pageSize;
    @ApiModelProperty(value = "전체 페이지 번호")
    private int totalPageNumber;

    @ApiModelProperty(value = "확인 해봐야함")
    private int size;

}
