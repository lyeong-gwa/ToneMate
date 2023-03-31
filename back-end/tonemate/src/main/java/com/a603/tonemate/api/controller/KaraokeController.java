package com.a603.tonemate.api.controller;

import com.a603.tonemate.api.service.KaraokeService;
import com.a603.tonemate.dto.KaraokeTopDto;
import com.a603.tonemate.dto.request.SearchSongReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.dto.response.KaraokeTopResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/karaoke")
@Api(tags = {"노래방 관련 API"})
public class KaraokeController {

    private final KaraokeService karaokeService;

    @ApiOperation(value = "노래방 Top 100 출력", notes = "금영, tj top 100 출력")
    @GetMapping("/top")
    public ResponseEntity<KaraokeTopResp> selectTopSong(@PageableDefault(size = 15) Pageable pageable) {

        System.out.println("옴?");
        System.out.println("사이즈: " + karaokeService.selectTopSong(pageable).getSongs());
        List<KaraokeTopDto> list = karaokeService.selectTopSong(pageable).getSongs();
        for (KaraokeTopDto a : list) {
            System.out.println(a.getKyTitle());
        }
        return new ResponseEntity<>(karaokeService.selectTopSong(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "노래방책 번호 출력", notes = "금영, tj 노래 출력")
    @GetMapping("/songs")
    public ResponseEntity<KaraokeResp> selectAllSongs(@PageableDefault(size = 15) Pageable pageable) {
        return new ResponseEntity<>(karaokeService.selectAllSong(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "노래 검색", notes = "금영, tj 번호 출력")
    @GetMapping("/search")
    public ResponseEntity<KaraokeResp> searchSong(@RequestBody SearchSongReq searchSongReq, @PageableDefault(size = 15) Pageable pageable) {
        return new ResponseEntity<>(karaokeService.searchSong(searchSongReq, pageable), HttpStatus.OK);
    }
}
