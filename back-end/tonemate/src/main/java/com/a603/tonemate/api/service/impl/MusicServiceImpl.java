package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.db.entity.*;
import com.a603.tonemate.db.repository.*;
import com.a603.tonemate.dto.common.PitchResult;
import com.a603.tonemate.dto.common.SingerDetail;
import com.a603.tonemate.dto.common.SingerSimilaritytmp;
import com.a603.tonemate.dto.response.*;
import com.a603.tonemate.enumpack.Genre;
import com.a603.tonemate.util.FlaskUtil;
import com.a603.tonemate.util.PitchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final FlaskUtil flaskUtil;
    private final PitchUtil pitchUtil;
    private final TimbreAnalysisRepository timbreAnalysisRepository;
    private final PitchAnalysisRepository pitchAnalysisRepository;
    private final SongRepository songRepository;
    private final SingerRepository singerRepository;
    private final SingerSimilarityRepository singerSimilarityRepository;

    @Transactional
    @Override
    public TimbreAnalysisResp saveTimbreAnalysis(Long userId, MultipartFile file) throws Exception {

        // 플라스크 서버에 분석 처리 요청 후 결과값 받아오기
        Map<String, Object> result = flaskUtil.requestTimbre(file);

        // 분석 결과 데이터 가공하여 응답
        if (result != null) {

            // 유사도 높은 순으로 정렬 (내림차순)
            TreeSet<SingerSimilaritytmp> set = new TreeSet<>((o1, o2) -> Float.compare(o2.getSimilarityPercent(), o1.getSimilarityPercent()));

            List<String> singers = (ArrayList<String>) result.get("singer");
            List<Double> similarityPercents = (ArrayList<Double>) result.get("similaritypercent");
            System.out.println("가수: " + singers);
            System.out.println("유사도: " + similarityPercents.get(0).floatValue());

            //timbre 엔티티 먼저 생성
            TimbreAnalysis timbreAnalysis = TimbreAnalysis.builder()
                    .userId(userId)
                    .mfccMean((Float) result.get("mfcc_mean"))
                    .stftMean((Float) result.get("stft_mean"))
                    .zcrMean((Float) result.get("zcr_mean"))
                    .spcMean((Float) result.get("spc_mean"))
                    .sprMean((Float) result.get("spr_mean"))
                    .rmsMean((Float) result.get("rms_mean"))
                    .mfccVar((Float) result.get("mfcc_var"))
                    .stftVar((Float) result.get("stft_var"))
                    .zcrVar((Float) result.get("zcr_var"))
                    .spcVar((Float) result.get("spc_var"))
                    .sprVar((Float) result.get("spr_var"))
                    .rmsVar((Float) result.get("rms_var"))
                    .build();
            timbreAnalysis = timbreAnalysisRepository.save(timbreAnalysis);
            for (int i = 0; i < singers.size(); i++) {
                String singerName = singers.get(i);
                float similarityPercent = similarityPercents.get(i).floatValue();
                set.add(new SingerSimilaritytmp(singerName, similarityPercent));
            }

            // 상위 5개의 객체 가져오기
            int top = 5;
            int cnt = 0;
            List<SingerSimilaritytmp> singerNames = new ArrayList<>();
            for (SingerSimilaritytmp detail : set) {
                if (cnt == top) {
                    break;
                }
                singerNames.add(detail);
                cnt++;
            }
            List<Singer> singerList = singerRepository.findByNameIn(singerNames.stream().map(SingerSimilaritytmp::getName).collect(Collectors.toList()));
            List<SingerSimilarity> singerSimilarities = new ArrayList<>();
            for (Singer singer : singerList) {
                for (SingerSimilaritytmp singerName : singerNames) {
                    if (singer.getName().equals(singerName.getName())) {
                        singerSimilarities.add(new SingerSimilarity(singer, singerName.getSimilarityPercent()));
                        break;
                    }
                }
            }
            singerSimilarityRepository.saveAll(singerSimilarities);

            // 분석 결과 응답 데이터 생성
            return TimbreAnalysisResp.builder()
                    .mfccMean(timbreAnalysis.getMfccMean())
                    .stftMean(timbreAnalysis.getStftMean())
                    .zcrMean(timbreAnalysis.getZcrMean())
                    .spcMean(timbreAnalysis.getSpcMean())
                    .sprMean(timbreAnalysis.getSprMean())
                    .rmsMean(timbreAnalysis.getRmsMean())
                    .mfccVar(timbreAnalysis.getMfccVar())
                    .stftVar(timbreAnalysis.getStftVar())
                    .zcrVar(timbreAnalysis.getZcrVar())
                    .spcVar(timbreAnalysis.getSpcVar())
                    .sprVar(timbreAnalysis.getSprVar())
                    .rmsVar(timbreAnalysis.getRmsVar())
                    .timbreId(timbreAnalysis.getTimbreId())
                    .time(timbreAnalysis.getTime())
                    .singerDetails(singerSimilarities.stream().map(o -> new SingerDetail(o.getSinger().getName(), o.getSimilarityPercent(), o.getSinger().getSongs().stream().map(this::toSongResp).collect(Collectors.toList()))).collect(Collectors.toList()))
                    .build();
        }
        return null;
    }

    @Override
    public PitchAnalysisResp savePitchAnalysis(PitchAnalysis pitchAnalysis) {
        return null;
    }

    @Override
    public ResultResp getResultList(Long userId) {

        List<PitchAnalysis> pitchAnalysisList = pitchAnalysisRepository.findAllByUserIdOrderByPitchIdDesc(userId);
        List<TimbreAnalysis> timbreAnalysisList = timbreAnalysisRepository.findAllByUserIdOrderByTimbreIdDesc(userId);

        List<TimbreResultResp> timbreResultRespList = timbreAnalysisList
                .stream()
                .map(x -> TimbreResultResp.builder()
                        .timbreId(x.getTimbreId())
                        .time(x.getTime())
                        .singer(x.getSingerSimilarities().stream()
                                .map(o -> o.getSinger().getName())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        List<PitchResultResp> pitchResultRespList = pitchAnalysisList
                .stream()
                .map(x -> PitchResultResp.builder()
                        .pitchId(x.getPitchId())
                        .time(x.getTime())
                        .lowOctave(pitchUtil.getOctaveName(x.getOctaveLow()))
                        .highOctave(pitchUtil.getOctaveName(x.getOctaveHigh()))
                        .build())
                .collect(Collectors.toList());

        return ResultResp.builder()
                .pitch(pitchResultRespList)
                .timbre(timbreResultRespList)
                .build();
    }

    @Transactional
    @Override
    public TimbreAnalysisResp selectOneTimbreAnalysis(Long timbreId) {

        TimbreAnalysis timbreAnalysis = timbreAnalysisRepository.findByTimbreId(timbreId).orElseThrow();

        return TimbreAnalysisResp.builder()
                .timbreId(timbreAnalysis.getTimbreId())
                .mfccMean(timbreAnalysis.getMfccMean())
                .stftMean(timbreAnalysis.getStftMean())
                .zcrMean(timbreAnalysis.getZcrMean())
                .spcMean(timbreAnalysis.getSpcMean())
                .sprMean(timbreAnalysis.getSprMean())
                .rmsMean(timbreAnalysis.getRmsMean())
                .mfccVar(timbreAnalysis.getMfccVar())
                .stftVar(timbreAnalysis.getStftVar())
                .zcrVar(timbreAnalysis.getZcrVar())
                .spcVar(timbreAnalysis.getSpcVar())
                .sprVar(timbreAnalysis.getSprVar())
                .rmsVar(timbreAnalysis.getRmsVar())
                .time(timbreAnalysis.getTime())
                .singerDetails(timbreAnalysis.getSingerSimilarities().stream().map(o -> new SingerDetail(
                                o.getSinger().getName(),
                                o.getSimilarityPercent(),
                                o.getSinger().getSongs().stream()
                                        .map(this::toSongResp)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public PitchAnalysisResp selectOnePitchAnalysis(Long pitchId) {

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchId(pitchId).orElseThrow();

        List<Long> possibleList = convertStringToLongList(pitchAnalysis.getPossibleList());
        List<Long> normalList = convertStringToLongList(pitchAnalysis.getNormalList());
        List<Long> impossibleList = convertStringToLongList(pitchAnalysis.getImpossibleList());

        List<Song> possibleSong = songRepository.findBySongIdIn(possibleList);
        List<Song> normalSong = songRepository.findBySongIdIn(normalList);
        List<Song> impossibleSong = songRepository.findBySongIdIn(impossibleList);

        return PitchAnalysisResp.builder()
                .lowOctave(pitchUtil.getOctaveName(pitchAnalysis.getOctaveLow()))
                .highOctave(pitchUtil.getOctaveName(pitchAnalysis.getOctaveHigh()))
                .possibleSong(possibleSong.subList(0, Math.min(3, possibleSong.size())))
                .normalSong(normalSong.subList(0, Math.min(3, normalSong.size())))
                .impossibleSong(impossibleSong.subList(0, Math.min(3, impossibleSong.size())))
                .pitchId(pitchAnalysis.getPitchId()).time(pitchAnalysis.getTime()).build();
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
        boolean gender = true;

        PitchResult lowPitch = pitchUtil.getPitch(lowFile, false);
        PitchResult highPitch = pitchUtil.getPitch(highFile, true);
        System.out.println(lowPitch + " " + highPitch);
        // 복구전까지 임시처리
        int randLow = new Random().nextInt(28);
        int randHigh = new Random().nextInt(27) + 32;

        List<Song> possibleSong = songRepository.findByGenderAndOctaveInRange(lowPitch.getPitch() + 1, highPitch.getPitch() - 1, gender,
                PageRequest.of(0, 50));
        List<Song> normalSong = songRepository.findByGenderAndOctaveInRange(lowPitch.getPitch(), highPitch.getPitch(), gender,
                PageRequest.of(0, 50));
        List<Song> impossibleSong = songRepository.findByGenderAndOctaveOverlap(lowPitch.getPitch(), highPitch.getPitch(), gender,
                PageRequest.of(0, 50));
        List<Long> possibleSongId = new ArrayList<>();
        List<Long> normalSongId = new ArrayList<>();
        List<Long> impossibleSongId = new ArrayList<>();

        //50 이하의 데이터에서 for문과 큰 성능 차이가 없다. forEach가 더 유연한 대처가능
        possibleSong.forEach(song -> possibleSongId.add(song.getSongId()));
        normalSong.forEach(song -> normalSongId.add(song.getSongId()));
        impossibleSong.forEach(song -> impossibleSongId.add(song.getSongId()));

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.save(PitchAnalysis.builder().octaveLow(randLow)
                .octaveHigh(randHigh).userId(userId).possibleList(possibleSongId.toString())
                .normalList(normalSongId.toString()).impossibleList(impossibleSongId.toString()).build());

        return PitchAnalysisResp.builder().lowOctave(pitchUtil.getOctaveName(lowPitch.getPitch())).highOctave(pitchUtil.getOctaveName(highPitch.getPitch()))
                .possibleSong(possibleSong.subList(0, Math.min(3, possibleSong.size())))
                .normalSong(normalSong.subList(0, Math.min(3, normalSong.size())))
                .impossibleSong(impossibleSong.subList(0, Math.min(3, impossibleSong.size())))
                .pitchId(pitchAnalysis.getPitchId()).time(pitchAnalysis.getTime()).build();
    }

    @Override
    public PitchAnalysisResp analysisPitchByGenre(Long userId, String genre, Long pitchId) {
        Genre genreEnum = Genre.fromCode(genre);

        PitchAnalysis pitchAnalysis = pitchAnalysisRepository.findByPitchIdAndUserId(pitchId, userId).orElseThrow();

        List<Long> possibleList = convertStringToLongList(pitchAnalysis.getPossibleList());
        List<Long> normalList = convertStringToLongList(pitchAnalysis.getNormalList());
        List<Long> impossibleList = convertStringToLongList(pitchAnalysis.getImpossibleList());

        List<Song> possibleSongs = songRepository.findSingerByIdAndGenre(possibleList, genreEnum);
        List<Song> normalSongs = songRepository.findSingerByIdAndGenre(normalList, genreEnum);
        List<Song> impossibleSongs = songRepository.findSingerByIdAndGenre(impossibleList, genreEnum);

        return PitchAnalysisResp.builder()
                .possibleSong(possibleSongs)
                .normalSong(normalSongs)
                .impossibleSong(impossibleSongs)
                .build();
    }

    @Override
    public void deleteTimbreResult(Long resultId) {

    }

    @Override
    public void deletePitchResult(Long resultId) {

    }

    // 문자열 배열을 실제 리스트로 생성 str ="[1, 2, 3, 4]"
    private List<Long> convertStringToLongList(String str) {
        List<Long> convertList = new ArrayList<>();
        if ("[]".equals(str)) return convertList;

        String[] stringArray = str.substring(1, str.length() - 1).split(",");
        for (String s : stringArray) {
            String trimmed = s.trim();
            Long converted = Long.valueOf(trimmed);
            convertList.add(converted);
        }
        return convertList;
    }
}
