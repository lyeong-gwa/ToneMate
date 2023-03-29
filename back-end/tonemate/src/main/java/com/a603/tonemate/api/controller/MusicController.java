package com.a603.tonemate.api.controller;


import com.a603.tonemate.api.service.MusicService;
import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.db.entity.PitchAnalysis;
import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.db.entity.TimbreAnalysis;
import com.a603.tonemate.db.repository.SingerRepository;
import com.a603.tonemate.dto.response.PitchAnalysisResp;
import com.a603.tonemate.enumpack.Genre;
import com.a603.tonemate.security.auth.JwtTokenProvider;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Api(tags = {"음성 처리 API"})
@RequestMapping("/music")
public class MusicController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final SingerRepository singerRepo;
    private final MusicService musicService;


    @GetMapping("")
    public ResponseEntity<String> checkAlive() {
        return new ResponseEntity<String>("Alive", HttpStatus.OK);
    }


    @ApiOperation(value = "음색 분석", notes = "음색 검사를 위한 녹음 wav파일을 분석 및 저장")
    @PostMapping("/timbre")
    public ResponseEntity<?> analysisTimbre(@RequestParam("file_wav") MultipartFile file) throws Exception {//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
        System.out.println("파일 이름! "+ file.getOriginalFilename());

        /*
         * 1. wav파일을 flask에 전달한다. -> 가수 : [가수1,가수2,가수3,가수4,가수5] 유사도 : [유사도 1,유사도 2,유사도 3,유사도 4,유사도 5] 특성 : [..MFCC,STFT,ZCR등등.....]
         * 2. 우리가 받은 유사도는 그냥 가수들 중에서 어떤 가수인지에 대한 확률일 뿐이다. -> 가장 유사도가 높은 가수와 내 목소리의 유사도를 보정한다.
         * 3. 같은 방법으로 다른 가수들과의 유사도를 보정한다.
         * 4. 사용자의 특성을 이용해서 1위 가수의 노래들의 특성들과 비교해서 오차율이 가장 낮은 순서대로 10개의 노래를 나열한다.
         * 5. {가수배열, 유사도배열, 추천노래 10가지를 return한다.}
         * */
        
        TimbreAnalysis testTimbreAnalysis = TimbreAnalysis.builder().userId(1L).time(LocalDateTime.now()).build();
        musicService.saveTimbreAnalysis(testTimbreAnalysis);

        return new ResponseEntity<>("음색 분석", HttpStatus.OK);
    }


    @ApiOperation(value = "음역대 분석", notes = "음역대 검사를 위한 녹음 wav파일을 분석 및 저장")
    @PostMapping("/pitch")
    public ResponseEntity<?> analysisPitch(@RequestParam("lowOctave") MultipartFile low_file, @RequestParam("highOctave") MultipartFile high_file, @RequestParam("genre") String genre) {//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
    	PitchAnalysisResp result = musicService.analysisPitch(1L,low_file, high_file);

        return new ResponseEntity<PitchAnalysisResp>(result, HttpStatus.OK);
    }
    
    @ApiOperation(value = "음역대 분석 장르 요청", notes = "음역대 검사에서 장르에 따른 결과 제공")
    @GetMapping("/pitch/{genre}/{pitch_id}")
    public ResponseEntity<?> analysisPitchByGenre(@PathVariable("genre") String genre,@PathVariable("pitch_id") int pitch_id) {//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
    	PitchAnalysisResp result = musicService.analysisPitchByGenre(1L,genre, pitch_id);

        return new ResponseEntity<PitchAnalysisResp>(result, HttpStatus.OK);
    }
    

    @ApiOperation(value = "검사 결과 목록", notes = "사용자가 진행했던 분석결과를 리스트로 제공")
    @GetMapping("/result")
    public ResponseEntity<?> resultList() {//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
        /*
         * 음역대 검사, 음색검사 테이블에서 사용자에 해당하는 데이터만 추출하고
         * */

        musicService.getResultList(1L);

//        return new ResponseEntity<>("검사 결과 목록", HttpStatus.OK);
        return new ResponseEntity<>(musicService.getResultList(1L), HttpStatus.OK);
    }

    @ApiOperation(value = "검사 결과 삭제", notes = "사용자가 선택한 검사 결과를 삭제한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "검사 유형 (timbre/pitch)"),
            @ApiImplicitParam(name = "id", value = "검사 아이디")})
    @DeleteMapping("/result/{type}/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable("type") String type, @PathVariable("id") Long id) {//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
        /*
         * type에 따라서 음역대인지 음색인지 구분한다. id는 특정 테이블에 속한 결과 데이터의 id를 지정하고 해당 데이터를 삭제한다.
         * */
        musicService.deleteResult(type, id);

        return new ResponseEntity<>("검사 결과 삭제", HttpStatus.OK);
    }

    @ApiOperation(value = "검사 결과 조회", notes = "사용자가 선택한 검사 결과의 상세 정보를 조회한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "검사 유형 (timbre/pitch)"),
            @ApiImplicitParam(name = "id", value = "검사 아이디")})
    @GetMapping("/result/{type}/{id}")
    public ResponseEntity<?> selectOneResult(@PathVariable("type") String type, @PathVariable("id") Long id){//@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token
        /*
         * type에 따라서 음역대인지 음색인지 구분한다. id는 특정 테이블에 속한 결과 데이터의 id를 지정하고 해당 데이터를 조회한다..
         * */

        if (type.equals("timbre")) {
            return new ResponseEntity<>(musicService.selectOneTimbreAnalysis(id), HttpStatus.OK);
        } else if (type.equals("pitch")) {
            return new ResponseEntity<>(musicService.selectOnePitchAnalysis(id), HttpStatus.OK);
        }

        return new ResponseEntity<>("검사 결과 조회", HttpStatus.OK);
    }

}