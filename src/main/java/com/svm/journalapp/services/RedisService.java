package com.svm.journalapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);

            if(o == null){
                return null;
            }

            return entityClass.cast(o);

        } catch (Exception e){
            log.error("Error while getting key {} from Redis ", key, e);
            return null;
        }
    }

    //ttl: time to leave
    public void set(String key, Object o, Long ttl){
        try {
            redisTemplate.opsForValue().set(key, o, ttl, TimeUnit.SECONDS);
        } catch (Exception e){
            log.error("Error while setting key {} in Redis ", key, e);
        }
    }
}
