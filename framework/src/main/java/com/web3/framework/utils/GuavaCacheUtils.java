package com.web3.framework.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;

/**
 * @Author fuxian
 * @Date 2023/2/16
 */
public class GuavaCacheUtils {
    /**
     * 缓存值的最大数量
     */
    private int maximumSize = 1000;

    /**
     * 缓存过期分钟数
     */
    private long expireAfterAccessMinutes = 60 * 72;

    private static Cache<String, Object> cache = null;

    public GuavaCacheUtils() {
        super();
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public long getExpireAfterAccessMinutes() {
        return expireAfterAccessMinutes;
    }

    public void setExpireAfterAccessMinutes(long expireAfterAccessMinutes) {
        this.expireAfterAccessMinutes = expireAfterAccessMinutes;
    }

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(this.maximumSize)
                .expireAfterAccess(this.expireAfterAccessMinutes * 60, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 查询缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param obj
     */
    public static void put(String key, Object obj) {
        cache.put(key, obj);
    }

    /**
     * 失效缓存
     *
     * @param key
     */
    public static void invalidate(String key) {
        cache.invalidate(key);
    }
}
