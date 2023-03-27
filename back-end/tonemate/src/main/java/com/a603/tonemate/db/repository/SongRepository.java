package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySongId(Long i);
    
}
