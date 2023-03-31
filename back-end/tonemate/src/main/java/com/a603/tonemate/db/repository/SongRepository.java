package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Song;
import com.a603.tonemate.enumpack.Genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySongId(Long i);
    List<Song> findBySingerGenre(Genre genre);
//    List<Song> findFirst5BySingerId(Long singerId);
    List<Song> findTop3ByMfccMeanGreaterThanAndStftMeanLessThan(Float mfccMean, Float stftMean);
    List<Song> findTop3ByMfccMeanLessThanOrStftMeanGreaterThan(Float mfccMean, Float stftMean);
    List<Song> findTop3ByMfccMeanLessThanOrStftMeanGreaterThanAndSingerGenre(Float mfccMean, Float stftMean, Genre genre);
    List<Song> findTop3ByMfccMeanGreaterThanAndStftMeanLessThanAndSingerGenre(Float mfccMean, Float stftMean, Genre genre);
}
	
