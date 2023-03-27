package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.SongInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SongInfoRepository extends JpaRepository<SongInfo, Long> {
    Optional<SongInfo> findBySongId(Long i);
    
}
