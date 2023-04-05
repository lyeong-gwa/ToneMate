package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.SongResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;
import org.springframework.web.multipart.MultipartFile;

public interface MusicService {

    // 음색 검사 내용 추가
    TimbreAnalysisResp saveTimbreAnalysis(Long userId, MultipartFile file) throws Exception;

    // 음역대 검사 내용 추가
    PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis);

    // 검사 결과 목록 조회
    ResultResp getResultList(Long userId);

    // 음색 검사 결과 조회
    TimbreAnalysisResp selectOneTimbreAnalysis(Long timbreId);

    // 음역대 검사 결과 조회
    PitchAnalysisResp selectOnePitchAnalysis(Long pitchId);

    // 검사 결과 삭제
    void deleteResult(String type, Long resultId);

    // 사용자 목소리 wav파일을 받아서 유저의 최저음, 최고음 받기 String[0]은 최저음, String[1]은 최고음
    PitchAnalysisResp analysisPitch(Long userId, MultipartFile lowOctave, MultipartFile highOctave);

    // 사용자 음역대 검사 기록에 의한 요청처리
    PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, Long pitchId);

    // 음색 검사 결과 삭제
    void deleteTimbreResult(Long resultId);

    // 검사 결과 삭제
    void deletePitchResult(Long resultId);

    default SongResp toSongResp(Song song) {
        return SongResp.builder()
                .title(song.getTitle())
                .mfccMean(song.getMfccMean())
                .stftMean(song.getStftMean())
                .zcrMean(song.getZcrMean())
                .spcMean(song.getSpcMean())
                .sprMean(song.getSprMean())
                .rmsMean(song.getRmsMean())
                .mfccVar(song.getMfccVar())
                .stftVar(song.getStftVar())
                .zcrVar(song.getZcrVar())
                .spcVar(song.getSpcVar())
                .sprVar(song.getSprVar())
                .rmsVar(song.getRmsVar())
                .build();
    }
}
