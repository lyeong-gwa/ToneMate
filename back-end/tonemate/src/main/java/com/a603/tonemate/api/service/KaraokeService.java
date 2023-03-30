package com.a603.tonemate.api.service;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.dto.response.KaraokeResp;

import java.util.List;

public interface KaraokeService {
    //노래방 Top 100 출력
    List<KaraokeResp> selectTopSong();

    default KaraokeResp toDto(Karaoke karaoke) {
        return KaraokeResp.builder()
                .KaraokeId(karaoke.getKaraokeId())
                .kyNum(karaoke.getKyNum())
                .kyTitle(karaoke.getKyTitle())
                .kySinger(karaoke.getKySinger())
                .tjNum(karaoke.getTjNum())
                .tjTitle(karaoke.getTjTitle())
                .tjSinger(karaoke.getTjSinger())
                .build();

    }
}
