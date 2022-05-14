package com.cs307.project.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean set(String key, String value) {
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getStringSerializer();
                return connection.set(serializer.serialize(key), serializer.serialize(value));
            }
        });
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getStringSerializer();
                byte[] bytes = connection.get(serializer.serialize(key));
                return (String) serializer.deserialize(bytes);
            }
        });
    }

    @Override
    public boolean expire(String key, final long expireDate) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, expireDate, TimeUnit.MILLISECONDS));
    }
}
