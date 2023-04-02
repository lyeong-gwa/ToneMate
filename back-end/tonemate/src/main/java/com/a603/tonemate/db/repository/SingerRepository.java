package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SingerRepository extends JpaRepository<Singer, Long> {
    Optional<Singer> findBySingerId(Long i);

    Optional<Singer> findByName(String name);
}
