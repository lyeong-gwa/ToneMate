package com.a603.tonemate.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.db.repository.PitchAnalysisRepository;
import com.a603.tonemate.db.repository.SingerRepository;
import com.a603.tonemate.db.repository.SongRepository;
import com.a603.tonemate.db.repository.TimbreAnalysisRepository;
import com.a603.tonemate.enumpack.Genre;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SongFilterUtil {
	public List<Long> convertStringToLongList(String str) {
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
