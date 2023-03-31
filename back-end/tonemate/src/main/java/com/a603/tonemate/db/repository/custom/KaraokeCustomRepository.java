package com.a603.tonemate.db.repository.custom;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.dto.request.SearchSongReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KaraokeCustomRepository {
    Page<Karaoke> search(SearchSongReq param, Pageable pageable);

    Page<Karaoke> likeList(Long userId, Pageable pageable);
}
