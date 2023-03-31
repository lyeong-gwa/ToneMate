package com.a603.tonemate.db.repository.custom;

import com.a603.tonemate.db.entity.Karaoke;
import com.a603.tonemate.dto.request.SearchSongReq;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.a603.tonemate.db.entity.QKaraoke.karaoke;
import static com.a603.tonemate.db.entity.QLikeSong.likeSong;


@Repository
@RequiredArgsConstructor
public class KaraokeCustomRepositoryImpl implements KaraokeCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Karaoke> search(SearchSongReq param, Pageable pageable) {
        List<Karaoke> songs = query
                .select(karaoke)
                .from(karaoke)
                .where(containsSinger(param.getSinger()), containsTitle(param.getTitle()),
                        (containsKyNum(param.getNum())).or(containsTjNum(param.getNum())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(songs, pageable, songs.size());
    }

    @Override
    public Page<Karaoke> likeList(Long userId, Pageable pageable) {
        List<Karaoke> songs = query
                .select(karaoke)
                .from(karaoke)
                .rightJoin(likeSong)
                .on(likeSong.karaokeId.eq(karaoke.karaokeId))
                .where(likeSong.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(songs, pageable, songs.size());
    }

    private BooleanExpression containsSinger(String singer) {
        if (StringUtils.hasText(singer)) {
            return karaoke.singer.contains(singer);
        }
        return null;
    }

    private BooleanExpression containsTitle(String title) {
        if (StringUtils.hasText(title)) {
            return karaoke.singer.contains(title);
        }
        return null;
    }

    private BooleanExpression containsKyNum(Integer num) {
        if (num != null) {
            return karaoke.kyNum.eq(num);
        }
        return null;
    }

    private BooleanExpression containsTjNum(Integer num) {
        if (num != null) {
            return karaoke.tjNum.eq(num);
        }
        return null;
    }


}
