package com.ara.todayoutfit.post.repository;

import com.ara.todayoutfit.post.domain.PostLike;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ara.todayoutfit.post.domain.QPostLike.postLike;

@Repository
@RequiredArgsConstructor
public class PostLikeRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean likePost(Long seq, String ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(seq).append(":").append(ip);
        return redisTemplate.opsForValue().setIfAbsent(sb.toString(), LocalDateTime.now().toString());
    }

    public boolean deleteLikePost(Long seq, String ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(seq).append(":").append(ip);
        return redisTemplate.delete(sb.toString());
    }

}
