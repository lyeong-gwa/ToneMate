package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.PitchAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PitchAnalysisRepository extends JpaRepository<PitchAnalysis, Long> {
    Optional<PitchAnalysis> findBySingerId(Long i);
    
}
