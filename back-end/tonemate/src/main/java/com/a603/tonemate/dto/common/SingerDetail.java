package com.a603.tonemate.dto.common;

import com.a603.tonemate.db.entity.Singer;
import com.a603.tonemate.db.entity.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SingerDetail {

    // 가수 id
    private Long singerId;
    // 가수 이름
    private String name;
    // 유사도
    private Float similarityPercent;
    // 노래 목록
    private List<Song> songList;

    public SingerDetail(Singer singer, Float similarityPercent){
        this.singerId = singer.getSingerId();
        this.name = singer.getName();
        this.songList = singer.getSongs().subList(0, 5);
        this.similarityPercent = similarityPercent;
    }

    @Override
    public String toString() {
        return "[singerId=" + singerId +
                ", name=" + name +
                ", similarityPercent=" + similarityPercent + ']';
    }
}
