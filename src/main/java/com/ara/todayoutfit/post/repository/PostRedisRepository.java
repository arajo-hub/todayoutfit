package com.ara.todayoutfit.post.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;


    public Long increaseLikeCount(Long seq) {
        return redisTemplate.opsForValue().increment("post:like:" + seq);
    }

    public Long decreaseLikeCount(Long seq) {
        return redisTemplate.opsForValue().decrement("post:like:" + seq);
    }
}
