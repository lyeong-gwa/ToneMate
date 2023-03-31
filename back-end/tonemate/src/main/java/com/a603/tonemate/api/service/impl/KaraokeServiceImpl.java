package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.KaraokeService;
import com.a603.tonemate.db.repository.KaraokeRepository;
import com.a603.tonemate.db.repository.KaraokeTopRepository;
import com.a603.tonemate.dto.KaraokeDto;
import com.a603.tonemate.dto.KaraokeTopDto;
import com.a603.tonemate.dto.request.SearchSongReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.dto.response.KaraokeTopResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KaraokeServiceImpl implements KaraokeService {
    private final KaraokeTopRepository karaokeTopRepository;
    private final KaraokeRepository karaokeRepository;

    @Override
    public KaraokeTopResp selectTopSong(Pageable pageable) {
        Page<KaraokeTopDto> topSongs = karaokeTopRepository.findAll(pageable).map(this::toDto);

        return KaraokeTopResp.builder()
                .songs(topSongs.getContent())
                .pageSize(topSongs.getPageable().getPageSize())
                .totalPageNumber(topSongs.getTotalPages())
                .size(topSongs.getSize())
                .build();
    }

    @Override
    public KaraokeResp selectAllSong(Pageable pageable) {
        Page<KaraokeDto> allSongs = karaokeRepository.findAll(pageable).map(this::toDto);

        return KaraokeResp.builder()
                .songs(allSongs.getContent())
                .pageSize(allSongs.getPageable().getPageSize())
                .totalPageNumber(allSongs.getTotalPages())
                .size(allSongs.getSize())
                .build();
    }

    @Override
    public KaraokeResp searchSong(SearchSongReq searchSongReq, Pageable pageable) {
        Page<KaraokeDto> songs = karaokeRepository.search(searchSongReq, pageable).map(this::toDto);

        return KaraokeResp.builder()
                .songs(songs.getContent())
                .pageSize(songs.getPageable().getPageSize())
                .totalPageNumber(songs.getTotalPages())
                .size(songs.getSize())
                .build();
    }


    @Override
    public KaraokeResp findLikeList(Long userId, Pageable pageable) {
        Page<KaraokeDto> likeList = karaokeRepository.likeList(userId, pageable).map(this::toDto);
        return KaraokeResp.builder()
                .songs(likeList.getContent())
                .pageSize(likeList.getPageable().getPageSize())
                .totalPageNumber(likeList.getTotalPages())
                .size(likeList.getSize()).build();

    }


}
