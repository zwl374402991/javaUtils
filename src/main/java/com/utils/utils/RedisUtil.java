package com.utils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @autor: yangwp
 * @Description:
 * @Date Create in 10:21 2019/1/18
 */

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 添加字符串KEY
     *
     * @param key   字符串KEY
     * @param value 值 可以为对象
     */
    public void setValue(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 键值加上一个增量值
     */
    public Long increment(String key, long delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取key的过期时间
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 获取值并更新值
     */
    public void set(String key, String value, Long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 添加字符串KEY
     *
     * @param key      字符串KEY
     * @param value    值 可以为对象
     * @param expire   过期时间
     * @param timeUnit TimeUnit中定义的时间单位
     */
    public void addKey(String key, Object value, long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 根据KEY获取VALUE
     *
     * @param key
     * @return value
     */
    public Object getValue(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除
     *
     * @param key
     */
    public void delKey(String key) {
        this.redisTemplate.delete(key);
    }

    /**
     * 判断是否存在
     *
     * @param key
     */
    public Boolean existsKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 重命名key，如果存在则覆盖
     */
    public void renameKey(String oldKey, String newKey) {
        this.redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 添加集合
     *
     * @param key,value
     */
    public void setList(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合
     *
     * @param key
     * @return
     */
    public Object getList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }
}
