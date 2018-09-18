package com.zhs.demo.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.RedisClientInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public final class RedisUtils<K, V> implements Serializable {

    private RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 向 redis 中存入值
     *
     * @param key
     * @param value
     */
    public V set(K key, V value) {
        this.redisTemplate.opsForValue().set(key, value);
        return value;
    }


    /**
     *  检查 key 是否已存在
     *
     * @param key
     * @return
     */

    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 向 redis 中存入值，带失效时间，时间单位为 秒
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public V set(K key, V value, long expireTime) {
        this.redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return value;
    }


    /**
     * 向 redis 中存入值，带失效时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public V set(K key, V value, long expireTime, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, timeUnit);
        return value;
    }


    /**
     * 根据 key ，从 redis 中获取值
     *
     * @param key
     * @return
     */
    public V get(K key) {
        Object value = this.redisTemplate.opsForValue().get(key);
        if (value == null)
            return null;
        return (V) value;
    }


    /**
     * 查询多个 key 的值
     *
     * @param pattern
     * @return
     */
    public Set<V> keys(K pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 删除 key
     *
     * @param key
     * @return
     */
    public V remove(K key) {

        if (key == null) {
            return null;
        }

        ValueOperations<K, V> vo = this.redisTemplate.opsForValue();
        Object value = vo.get(key);
        if (value == null)
            return null;

        V returnValue = (V) value;
        redisTemplate.delete(key);

        return returnValue;

    }


    /**
     * 获取 客户端 列表
     *
     * @return
     */
    public List<RedisClientInfo> clientInfos() {
        List<RedisClientInfo> clientInfos = this.redisTemplate.getClientList();
        return clientInfos;
    }


    /**
     * 获取 key 的失效时间
     *
     * @param key
     * @return
     */
    public Long getExpire(K key) {
       return this.redisTemplate.getExpire(key);
    }


    /**
     * 计数器
     *
     * @param key
     * @param i  每次自增的数
     * @return
     */
    public void increment(String key, long i){
        redisTemplate.opsForValue().increment(key, i);
    }


    /**
     * 计数器
     *
     * @param key
     * @param i  每次自增的数
     * @return
     */
    public void increment(String key, double i){
        redisTemplate.opsForValue().increment(key, i);
    }

}