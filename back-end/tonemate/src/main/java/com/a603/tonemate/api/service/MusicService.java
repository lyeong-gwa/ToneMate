package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.dto.request.PitchAnalysisReq;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {

    // 음색 검사 내용 추가
    TimbreAnalysisResp saveTimbreAnalysis(Long userId, MultipartFile file) throws Exception;

    // 음역대 검사 내용 추가
    PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis);

    // 검사 결과 목록 조회
    List<ResultResp> getResultList(Long userId);

    // 음색 검사 결과 조회
    TimbreAnalysisResp selectOneTimbreAnalysis(Long timbreId);

    // 음역대 검사 결과 조회
    PitchAnalysisResp selectOnePitchAnalysis(Long userId, Long pitchId);

    // 검사 결과 삭제
    void deleteResult(String type, Long resultId);

    // 사용자 목소리 wav파일을 받아서 유저의 최저음, 최고음 받기 String[0]은 최저음, String[1]은 최고음
    PitchAnalysisResp analysisPitch(Long userId, MultipartFile highOctave, MultipartFile lowOctave);
    
    
    // 사용자 음역대 검사 기록에 의한 요청처리
	PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, Long pitchId);
}
