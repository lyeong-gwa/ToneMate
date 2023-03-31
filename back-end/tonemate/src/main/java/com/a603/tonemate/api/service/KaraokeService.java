package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.db.entity.KaraokeTop;
import com.a603.tonemate.dto.KaraokeDto;
import com.a603.tonemate.dto.KaraokeTopDto;
import com.a603.tonemate.dto.request.SearchSongReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.dto.response.KaraokeTopResp;
import org.springframework.data.domain.Pageable;

public interface KaraokeService {
    //노래방 Top 100 출력
    KaraokeTopResp selectTopSong(Pageable pageable);

    //등록되 있는 모든 노래 출력
    KaraokeResp selectAllSong(Pageable pageable);

    //노래 검색
    KaraokeResp searchSong(SearchSongReq searchSongReq, Pageable pageable);

    KaraokeResp findLikeList(Long userId, Pageable pageable);

    default KaraokeTopDto toDto(KaraokeTop karaokeTop) {
        return KaraokeTopDto.builder()
                .karaokeTopId(karaokeTop.getKaraokeTopId())
                .kyNum(karaokeTop.getKyNum())
                .kyTitle(karaokeTop.getKyTitle())
                .kySinger(karaokeTop.getKySinger())
                .tjNum(karaokeTop.getTjNum())
                .tjTitle(karaokeTop.getTjTitle())
                .tjSinger(karaokeTop.getTjSinger())
                .build();

    }

    default KaraokeDto toDto(Karaoke karaoke) {
        return KaraokeDto.builder()
                .tjNum(karaoke.getTjNum())
                .kyNum(karaoke.getKyNum())
                .singer(karaoke.getSinger())
                .title(karaoke.getTitle())
                .build();

    }
}
