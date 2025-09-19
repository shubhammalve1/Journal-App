package com.svm.journalapp.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testRedis(){
        //redisTemplate.opsForValue().set("email", "shubhammalve098@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        System.out.println(email);
        int num = 1;
    }
}