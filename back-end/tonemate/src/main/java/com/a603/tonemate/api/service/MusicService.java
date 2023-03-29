package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.TimbreAnalysis;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface MusicService {

    // 음색 검사 내용 추가
    TimbreAnalysisResp saveTimbreAnalysis(TimbreAnalysis timbreAnalysis);

    // 음역대 검사 내용 추가
    PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis);

    // 검사 결과 목록 조회
    List<ResultResp> getResultList(Long userId);

    // 음색 검사 결과 조회
    TimbreAnalysisResp selectOneTimbreAnalysis(Long timbreId);

    // 음역대 검사 결과 조회
    PitchAnalysisResp selectOnePitchAnalysis(Long pitchId);

    // 검사 결과 삭제
    void deleteResult(String type, Long resultId);
    
    // flask에게 파일주고 결과값 요청
    Map<String,Object> requestFlaskTimbre(MultipartFile file) throws Exception;
}
