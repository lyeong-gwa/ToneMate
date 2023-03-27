package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.TimbreAnalysis;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;

import java.util.List;

public interface MusicService {

    // 음색 검사 내용 추가
    TimbreAnalysisResp saveTimbreAnalysis(TimbreAnalysis timbreAnalysis);

    // 음역대 검사 내용 추가
    PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis);

    // 검사 결과 목록 조회
    List<ResultResp> getResultList(Long userId);

    // 검사 결과 삭제
    void deleteResult(String type, Long resultId);
}
