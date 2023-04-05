package com.a603.tonemate.db.entity;

import com.a603.tonemate.enumpack.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_genre", columnList = "genre")})//index
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "singer_id")
    private Long singerId;
    private String name;
    @Column(nullable = true)
    private Boolean gender;
    private Date birthYear;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(100) default 'UNKNOW'")
    private Genre genre;
    @OneToMany
    @JoinColumn(name = "singer_id")
    @BatchSize(size = 5)
    @JsonBackReference // Song을 dto로 변경 후 어노테이션 삭제 예정
    private List<Song> songs;
}
