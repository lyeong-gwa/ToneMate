package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.api.util.FlaskUtil;
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
import com.a603.tonemate.dto.response.SingerDetailResp;
import com.a603.tonemate.dto.response.TimbreAnalysisResp;
import com.a603.tonemate.enumpack.Genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final FlaskUtil flaskUtil;
    private final PitchUtil pitchUtil;
    private final TimbreAnalysisRepository timbreAnalysisRepository;
    private final PitchAnalysisRepository pitchAnalysisRepository;
    private final SongRepository songRepository;

    @Override
    public TimbreAnalysisResp saveTimbreAnalysis(Long userId, MultipartFile file) throws Exception {

        // 플라스크 서버에 분석 처리 요청 후 결과값 받아오기
        Map<String, Object> result = flaskUtil.requestTimbre(file);

        // 분석 결과 데이터 가공하여 응답
        if (result != null) {

            // 유사도 높은 순으로 정렬 (내림차순)
            TreeSet<SingerDetailResp> set = new TreeSet<>();

            List<String> singers = (ArrayList<String>) result.get("singer");
            List<Double> similarityPercents = (ArrayList<Double>) result.get("similaritypercent");
            System.out.println("가수: " + singers);
            System.out.println("유사도: " + similarityPercents.get(0).floatValue());

            for (int i = 0; i < singers.size(); i++) {
                Long singerId = Long.valueOf((i + 1));
//                  Long singerId = singerRepository.findByName(singerName).get().getSingerId();
                String singerName = singers.get(i);
                float similarityPercent = similarityPercents.get(i).floatValue();
                set.add(new SingerDetailResp(singerId, singerName, similarityPercent));
            }

            // 상위 5개의 객체 가져오기
            List<SingerDetailResp> topFive = new ArrayList<>();
            for (SingerDetailResp detail : set) {
                detail.setSongList(songRepository.findFirst5BySingerId(detail.getSingerId()));
                topFive.add(detail);
                if (topFive.size() == 5) {
                    break;
                }
            }
            System.out.println("정렬된 결과: " + set);
            System.out.println("상위 5개: " + topFive);

            TimbreAnalysis newTimbreAnalysis = TimbreAnalysis.builder()
                    .userId(userId)
                    .mfccMean(((Double)result.get("mfcc_mean")).floatValue())
                    .stftMean(((Double)result.get("stft_mean")).floatValue())
                    .zcrMean(((Double)result.get("zcr_mean")).floatValue())
                    .spcMean(((Double)result.get("spc_mean")).floatValue())
                    .sprMean(((Double)result.get("spr_mean")).floatValue())
                    .rmsMean(((Double)result.get("rms_mean")).floatValue())
                    .mfccVar(((Double)result.get("mfcc_mean")).floatValue())
                    .stftVar(((Double)result.get("stft_mean")).floatValue())
                    .zcrVar(((Double)result.get("zcr_mean")).floatValue())
                    .spcVar(((Double)result.get("spc_mean")).floatValue())
                    .sprVar(((Double)result.get("spr_mean")).floatValue())
                    .rmsVar(((Double)result.get("rms_mean")).floatValue())
                    .singer1(topFive.get(0).getSingerId())
                    .singer2(topFive.get(1).getSingerId())
                    .singer3(topFive.get(2).getSingerId())
                    .singer4(topFive.get(3).getSingerId())
                    .singer5(topFive.get(4).getSingerId())
                    .similarity1(topFive.get(0).getSimilarityPercent())
                    .similarity2(topFive.get(1).getSimilarityPercent())
                    .similarity3(topFive.get(2).getSimilarityPercent())
                    .similarity4(topFive.get(3).getSimilarityPercent())
                    .similarity5(topFive.get(4).getSimilarityPercent())
                    .time(LocalDateTime.now()).build();

            // 분석 결과 데이터베이스에 저장
            TimbreAnalysis saveTimbreAnalysis = timbreAnalysisRepository.save(newTimbreAnalysis);

            List<Long> singerIdList = topFive.stream().map(SingerDetailResp::getSingerId).collect(Collectors.toList());
            // 분석 결과 응답 데이터 생성
            TimbreAnalysisResp timbreAnalysisResp = TimbreAnalysisResp.builder()
                    .mfccMean(saveTimbreAnalysis.getMfccMean())
                    .stftMean(saveTimbreAnalysis.getStftMean())
                    .zcrMean(saveTimbreAnalysis.getZcrMean())
                    .spcMean(saveTimbreAnalysis.getSpcMean())
                    .sprMean(saveTimbreAnalysis.getSprMean())
                    .rmsMean(saveTimbreAnalysis.getRmsMean())
                    .mfccVar(saveTimbreAnalysis.getMfccVar())
                    .stftVar(saveTimbreAnalysis.getStftVar())
                    .zcrVar(saveTimbreAnalysis.getZcrVar())
                    .spcVar(saveTimbreAnalysis.getSpcVar())
                    .sprVar(saveTimbreAnalysis.getSprVar())
                    .rmsVar(saveTimbreAnalysis.getRmsVar())
                    .timbreId(saveTimbreAnalysis.getTimbreId())
                    .time(saveTimbreAnalysis.getTime())
                    .singerDetails(topFive)
                    .build();
            return timbreAnalysisResp;
        }

        return null;
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
