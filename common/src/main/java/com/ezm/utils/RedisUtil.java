package com.ezm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取缓存数据
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {

        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 指定数据库获取缓存数据
     *
     * @param key 键
     * @return 值
     */
    public Object get(int db, String key) {
        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(db);//切换到指定的db上(index指具体的值)
        redisTemplate.setConnectionFactory(jedisConnectionFactory);//执行切换操作
        jedisConnectionFactory.afterPropertiesSet();
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {

        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 指定数据库放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public boolean set(int db, String key, Object value) {

        try {
            LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
            jedisConnectionFactory.setDatabase(db);//切换到指定的db上(index指具体的值)
            redisTemplate.setConnectionFactory(jedisConnectionFactory);//执行切换操作
            jedisConnectionFactory.resetConnection();
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description(描述)： 指定数据库放入缓存并设置失效时间
     * @Author(开发人员)： Kukyu
     * @date(日期)： 2020.12.14 00:50
     * @param: db
     * @param: key
     * @param: value
     * @param: time
     * @return(返回值)：boolean
     */
    public boolean set(int db, String key, Object value, long time) {

        try {
            if (time > 0) {
                LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
                jedisConnectionFactory.setDatabase(db);//切换到指定的db上(index指具体的值)
                redisTemplate.setConnectionFactory(jedisConnectionFactory);//执行切换操作
                jedisConnectionFactory.resetConnection();
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除key
     *
     * @param key 键
     * @return boolean
     */
    public boolean remove(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除指定 redis 库的 key
     *
     * @param db  数据库
     * @param key 键
     * @return boolean
     */
    public boolean remove(int db, String key) {
        try {
            LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
            jedisConnectionFactory.setDatabase(db);//切换到指定的db上(index指具体的值)
            redisTemplate.setConnectionFactory(jedisConnectionFactory);//执行切换操作
            jedisConnectionFactory.resetConnection();
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
