package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Song;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySongId(Long i);
    List<Song> findFirst5BySingerId(Long singerId);
    
    @Query("SELECT s FROM Song s JOIN Singer si ON s.singerId = si.singerId WHERE si.gender = :gender AND s.octaveLow >= :octaveLow AND s.octaveHigh <= :octaveHigh ORDER BY ABS(((s.octaveLow + s.octaveHigh) / 2) - ((:octaveLow + :octaveHigh) / 2))")
    List<Song> findByGenderAndOctaveInRange(@Param("octaveLow") int octaveLow, @Param("octaveHigh") int octaveHigh, @Param("gender") boolean gender,Pageable pageable);

    @Query("SELECT s FROM Song s JOIN Singer si ON s.singerId = si.singerId WHERE si.gender = :gender AND s.octaveLow <= :octaveHigh AND s.octaveHigh >= :octaveLow ORDER BY ABS(((s.octaveLow + s.octaveHigh) / 2) - ((:octaveLow + :octaveHigh) / 2))")
    List<Song> findByGenderAndOctaveOverlap(@Param("octaveLow") int octaveLow, @Param("octaveHigh") int octaveHigh, @Param("gender") boolean gender, Pageable pageable);
    
}


