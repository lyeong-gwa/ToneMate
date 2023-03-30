package com.a603.tonemate.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.a603.tonemate.db.entity.Song;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "음색 분석 검사 결과", description = "사용자가 보낸 wav 파일에 대한 음색 분석 결과 정보들이 담김")
public class PitchAnalysisResp {
    @ApiModelProperty(value = "음역대 분석 결과 id")
    private Long pitchId;

    @ApiModelProperty(value = "사용자 음역대의 최저음")
    private String lowOctave;

    @ApiModelProperty(value = "사용자 음역대의 최고음")
    private String high_Octave;

    @ApiModelProperty(value = "음역대 분석 검사 일시")
    private LocalDateTime time;
    
    @ApiModelProperty(value = "잘 부를 수 있는 노래")
    private List<Song> passibleSong;
    
    @ApiModelProperty(value = "적당히 부를 수 있는 노래")
    private List<Song> normalSong;
    
    @ApiModelProperty(value = "못 부르는 노래")
    private List<Song> impassibleSong;
    
    
}
