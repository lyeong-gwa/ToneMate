package com.a603.tonemate.dto.response;

import com.a603.tonemate.db.entity.Song;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class SingerDetailResp implements Comparable<SingerDetailResp> {

    private Long singerId;
    private String name;
    private Float similarityPercent;
    private List<Song> songList;

    public SingerDetailResp(Long singerId, String name, Float similarityPercent){
        this.singerId = singerId;
        this.name = name;
        this.similarityPercent = similarityPercent;
    }

    // 유사도 높은 순으로 정렬 (내림차순)
    @Override
    public int compareTo(SingerDetailResp o){
        if (o.similarityPercent > this.similarityPercent){
            return 1;
        } else if (o.similarityPercent < this.similarityPercent){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "[singerId=" + singerId +
                ", name=" + name +
                ", similarityPercent=" + similarityPercent + ']';
    }
}
