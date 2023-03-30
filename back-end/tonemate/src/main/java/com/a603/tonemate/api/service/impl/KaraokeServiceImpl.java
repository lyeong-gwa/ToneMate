package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.KaraokeService;
import com.a603.tonemate.db.repository.KaraokeRepository;
import com.a603.tonemate.dto.response.KaraokeResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KaraokeServiceImpl implements KaraokeService {
    private final KaraokeRepository karaokeRepository;

    @Override
    public List<KaraokeResp> selectTopSong() {
        return karaokeRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }


}
