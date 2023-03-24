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
public class Songinfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songKey;
    private Long singerKey;
    private String title;
    private String num_ky;
    private String num_tj;


    @Builder
	public Songinfo(Long songKey, Long singerKey, String title, String num_ky, String num_tj) {
		super();
		this.songKey = songKey;
		this.singerKey = singerKey;
		this.title = title;
		this.num_ky = num_ky;
		this.num_tj = num_tj;
	}



}
