package com.a603.tonemate.util;

import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.db.repository.SongRepository;
import com.a603.tonemate.enumpack.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongFilterUtil {
    public List<Long> convertStringToLongList(String str) {
        if ("[]".equals(str)) return new ArrayList<Long>();
        return Arrays.stream(str.substring(1, str.length() - 1).split(","))
                .map(String::trim).map(Long::valueOf).collect(Collectors.toList());
    }

    public List<Song> getSongsBySongIdsAndGenre(SongRepository songRepository, List<Long> songIds, Genre genre) {
        return songIds.stream()
                .map(songRepository::findBySongId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(song -> song.getSinger().getGenre() == genre)
                .limit(3)
                .collect(Collectors.toList());
    }
}
