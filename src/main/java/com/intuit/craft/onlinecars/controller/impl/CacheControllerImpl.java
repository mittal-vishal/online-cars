package com.intuit.craft.onlinecars.controller.impl;

import com.intuit.craft.onlinecars.constant.Constant;
import com.intuit.craft.onlinecars.controller.CacheController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheControllerImpl implements CacheController {

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public CacheControllerImpl(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ResponseEntity<Void> evictCache() {
        redisTemplate.delete(Constant.REDIS_HASH_CAR);
        redisTemplate.delete(Constant.REDIS_HASH_SIMILARITY);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
