package com.a603.tonemate.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class SongInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private Long singerId;
    private String title;
    private String num_ky;
    private String num_tj;
	
    
    @Builder
    public SongInfo(Long songId, Long singerId, String title, String num_ky, String num_tj) {
		super();
		this.songId = songId;
		this.singerId = singerId;
		this.title = title;
		this.num_ky = num_ky;
		this.num_tj = num_tj;
	}


    
	



}
