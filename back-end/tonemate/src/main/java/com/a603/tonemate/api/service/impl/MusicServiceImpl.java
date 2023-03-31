package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.api.util.PitchUtil;
import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.db.entity.TimbreAnalysis;
import com.a603.tonemate.db.repository.PitchAnalysisRepository;
import com.a603.tonemate.db.repository.SongRepository;
import com.a603.tonemate.db.repository.TimbreAnalysisRepository;
import com.a603.tonemate.dto.common.PitchResult;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.dto.response.ResultResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;
import com.a603.tonemate.enumpack.Genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final PitchUtil pitchUtil;
    private final TimbreAnalysisRepository timbreAnalysisRepository;
    private final PitchAnalysisRepository pitchAnalysisRepository;
    private final SongRepository songRepository;

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
        PitchResult lowPitch = pitchUtil.getPitch(lowFile, false);
        PitchResult highPitch = pitchUtil.getPitch(highFile, true);
        List<Song> passibleSong = songRepository.findTop3ByMfccMeanGreaterThanAndStftMeanLessThan(0.2f,0.2f);
        List<Song> normalSong = songRepository.findTop3ByMfccMeanGreaterThanAndStftMeanLessThan(0.1f,0.3f);
        List<Song> impassibleSong = songRepository.findTop3ByMfccMeanLessThanOrStftMeanGreaterThan(0.1f,0.3f);

        
        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.save(PitchAnalysis.builder()
        		.octaveLow(lowPitch.getPitch())
        		.octaveHigh(highPitch.getPitch())
        		.userId(userId)
        		.build());
        
        return PitchAnalysisResp.builder()
        		.lowOctave(pitchUtil.getOctaveName(lowPitch.getPitch()))
        		.highOctave(pitchUtil.getOctaveName(highPitch.getPitch()))
        		.passibleSong(passibleSong)
        		.normalSong(normalSong)
        		.impassibleSong(impassibleSong)
        		.pitchId(pitchAnalysis.getPitchId())
        		.time(pitchAnalysis.getTime())
        		.build();
    }

    @Override
    public PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, Long pitchId) {
    	PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchIdAndUserId(pitchId, userId).orElseThrow();
    	
    	//pitchAnalysis.getOctaveLow(), pitchAnalysis.getOctaveHigh(), Genre.fromCode(genre) -> passibleSong이 normalSong에 포함되는 로직이 되버림 -> 데이터 준비되면 예외처리하기
    	System.out.println(genre);
    	System.out.println(Genre.fromCode(genre));
    	List<Song> passibleSong = songRepository.findTop3ByMfccMeanLessThanOrStftMeanGreaterThanAndSingerGenre(0.2f,0.2f,Genre.fromCode(genre));
    	List<Song> normalSong = songRepository.findTop3ByMfccMeanLessThanOrStftMeanGreaterThanAndSingerGenre(0.1f,0.3f,Genre.fromCode(genre));
    	List<Song> impassibleSong = songRepository.findTop3ByMfccMeanGreaterThanAndStftMeanLessThanAndSingerGenre(0.1f,0.3f,Genre.fromCode(genre));

        return PitchAnalysisResp.builder()
        		.passibleSong(passibleSong)
        		.normalSong(normalSong)
        		.impassibleSong(impassibleSong)
        		.build();
    }

}
