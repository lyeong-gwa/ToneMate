package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.SongAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SongAnalysisRepository extends JpaRepository<SongAnalysis, Long> {
    Optional<SongAnalysis> findBySongId(Long i);
    
}
