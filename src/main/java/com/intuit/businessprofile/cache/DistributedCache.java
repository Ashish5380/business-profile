package com.intuit.businessprofile.cache;

import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.util.concurrent.TimeUnit;

public interface DistributedCache extends Cache {

    RedisAtomicInteger atomicInteger(String key);

    RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit);

}
