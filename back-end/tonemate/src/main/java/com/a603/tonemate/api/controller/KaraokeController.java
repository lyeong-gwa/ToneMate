package com.a603.tonemate.api.controller;

import com.a603.tonemate.api.service.KaraokeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"노래방 관련 API"})
public class KaraokeController {

    private final KaraokeService songService;

//    @ApiOperation(value = "노래방 Top 100 출력", notes = "금영, tj top 100 출력")
//    @GetMapping("top-songs")
//    public ResponseEntity<TopSongsResp> selectTopSong() {
//        return new ResponseEntity<>(songService.selectTopSong(), HttpStatus.OK);
//    }


}
