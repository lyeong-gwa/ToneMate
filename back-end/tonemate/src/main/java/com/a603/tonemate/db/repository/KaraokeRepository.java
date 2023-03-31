package com.a603.tonemate.db.repository;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.db.repository.custom.KaraokeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaraokeRepository extends JpaRepository<Karaoke, Long>, KaraokeCustomRepository {

}
