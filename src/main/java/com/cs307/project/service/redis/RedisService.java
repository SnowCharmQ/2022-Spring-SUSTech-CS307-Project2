package com.cs307.project.service.redis;

import org.springframework.stereotype.Service;

@Service
public interface RedisService {
    boolean set(String key, String value);
    String get(String key);
    boolean expire(String key, long expireDate);
}
