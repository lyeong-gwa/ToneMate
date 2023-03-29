package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.api.util.PitchUtil;
import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.db.entity.TimbreAnalysis;
import com.a603.tonemate.db.repository.PitchAnalysisRepository;
import com.a603.tonemate.db.repository.TimbreAnalysisRepository;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final PitchUtil pitchUtil;
    private final TimbreAnalysisRepository timbreAnalysisRepository;
    private final PitchAnalysisRepository pitchAnalysisRepository;
    
    @Override
    public TimbreAnalysisResp saveTimbreAnalysis(TimbreAnalysis timbreAnalysis) {

        TimbreAnalysis newTimbreAnalysis = TimbreAnalysis.builder()
                .timbreId(timbreAnalysis.getTimbreId())
                .userId(timbreAnalysis.getUserId())
                .time(LocalDateTime.now()).build();

        TimbreAnalysis saveTimbreAnalysis = timbreAnalysisRepository.save(newTimbreAnalysis);

        TimbreAnalysisResp timbreAnalysisResp = TimbreAnalysisResp.builder()
                .timbreId(saveTimbreAnalysis.getTimbreId())
                .time(saveTimbreAnalysis.getTime())
                .build();

        return timbreAnalysisResp;
    }

    @Override
    public PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis) {

        PitchAnalysis newPitchAnalysis = PitchAnalysis.builder()
                .pitchId(pitchAnalysis.getPitchId())
                .userId(pitchAnalysis.getUserId())
                .time(LocalDateTime.now()).build();

        PitchAnalysis savePitchAnalysis = pitchAnalysisRepository.save(newPitchAnalysis);

        PitchAnalysisResp pitchAnalysisResp = PitchAnalysisResp.builder()
                .pitchId(savePitchAnalysis.getPitchId())
                .time(savePitchAnalysis.getTime())
                .build();

        return pitchAnalysisResp;

    }

    @Override
    public List<ResultResp> getResultList(Long userId) {

        List<PitchAnalysis> pitchAnalysisList = pitchAnalysisRepository.findAllByUserId(userId);
        List<TimbreAnalysis> timbreAnalysisList = timbreAnalysisRepository.findAllByUserId(userId);

        List<ResultResp> resultRespList = Stream.concat(
                        timbreAnalysisList.stream().map(x -> ResultResp.builder()
                                .resultId(x.getTimbreId())
                                .type("timbre")
                                .time(x.getTime()).build()),
                        pitchAnalysisList.stream().map(x -> ResultResp.builder()
                                .resultId(x.getPitchId())
                                .type("pitch")
                                .time(x.getTime()).build()))
                .collect(Collectors.toList());

        Collections.sort(resultRespList);

        return resultRespList;
    }

    @Override
    public TimbreAnalysisResp selectOneTimbreAnalysis(Long timbreId) {

        TimbreAnalysis timbreAnalysis = timbreAnalysisRepository.findByTimbreId(timbreId).orElseThrow();

        TimbreAnalysisResp timbreAnalysisResp = TimbreAnalysisResp.builder()
                .timbreId(timbreAnalysis.getTimbreId())
                .time(timbreAnalysis.getTime())
                .build();

        return timbreAnalysisResp;
    }

    @Override
    public PitchAnalysisResp selectOnePitchAnalysis(Long pitchId) {

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchId(pitchId).orElseThrow();

        PitchAnalysisResp pitchAnalysisResp = PitchAnalysisResp.builder()
                .pitchId(pitchAnalysis.getPitchId())
                .time(pitchAnalysis.getTime())
                .build();

        return pitchAnalysisResp;
    }

    @Override
    public void deleteResult(String type, Long resultId) {
        // 음색 검사 결과 삭제
        if (type.equals("timbre")) {
            timbreAnalysisRepository.deleteById(resultId);
        } else if (type.equals("pitch")) {
            pitchAnalysisRepository.deleteById(resultId);
        }
    }


	@Override
	public PitchAnalysisResp analysisPitch(Long userId, MultipartFile lowFile, MultipartFile highFile) {
        String lowPitch = pitchUtil.getPitch(lowFile, false);
        String highPitch = pitchUtil.getPitch(highFile, true);
		
		return null;
	}

	@Override
	public PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, int pitchId) {
		
		
		return null;
	}
	
}
