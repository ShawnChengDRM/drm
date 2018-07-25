package com.naiye.drm.component.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.regex.Pattern;

@Component
public class RedisComponent {
    private static final Logger logger = LoggerFactory.getLogger(RedisComponent.class);
    private static final int DEFAULT_DB = 0;

    private RedisSerializer serializer;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public void set(final String key, final Object value) {
        redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            redisConnection.set(SafeEncoder.encode(key), serialize(value));
            return null;
        });
    }

    public Long del(final String key){
        return (Long) redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            return redisConnection.del(SafeEncoder.encode(key));
        });
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public void setex(final String key,final Object value, Long expireTime) {
        redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            redisConnection.setEx(SafeEncoder.encode(key), expireTime, serialize(value));
            return null;
        });
    }

    public Long incrby(final String key, Long increment){
        return (Long) redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.incrBy(SafeEncoder.encode(key), increment));
    }

    public Long hincrby(final String key, final String field, Long increment){
        return (Long) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.hIncrBy(SafeEncoder.encode(key), SafeEncoder.encode(field), increment));
    }

    public boolean expire(final String key, Long timeout){
        return (boolean) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.expire(SafeEncoder.encode(key), timeout));
    }

    public boolean hsetnx(final String key, final String field, final Object value) {
        return (boolean) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.hSetNX(SafeEncoder.encode(key), SafeEncoder.encode(field), serialize(value)));
    }

    public boolean hset(final String key, final String field, final Object value) {
        return (boolean) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.hSet(SafeEncoder.encode(key), SafeEncoder.encode(field), serialize(value)));
    }

    public <T> T get(final String key, Class<T> clazz) {
        return (T) redisTemplate.execute((RedisCallback<T>) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            byte[] bs = redisConnection.get(SafeEncoder.encode(key));
            return deserialize(bs, clazz);
        });
    }

    public <T> T hget(final String key,final String field, Class<T> clazz) {
        return (T) redisTemplate.execute((RedisCallback<T>) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            byte[] bs = redisConnection.hGet(SafeEncoder.encode(key), SafeEncoder.encode(field));
            return deserialize(bs, clazz);
        });
    }

    public Map<String, Object> hgetall(final String key) {
        return (Map<String, Object>) redisTemplate.execute((RedisCallback<Map<String, Object>>) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            Map<byte[], byte[]> byteMap = redisConnection.hGetAll(SafeEncoder.encode(key));
            if(byteMap == null) return null;
            Map<String, Object> res = new HashMap<>();
            byteMap.forEach((key1, value) -> res.put(deserialize(key1, String.class).toString(), deserialize(value, Object.class)));
            return res;
        });
    }

    public void lpush(final String key, final Object value) {
        redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            redisConnection.lPush(SafeEncoder.encode(key), serialize(value));
            return null;
        });
    }

    public <T> T rpop(final String key, Class<T> clazz) {
        return (T) redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            byte[] bs = redisConnection.rPop(SafeEncoder.encode(key));
            return deserialize(bs, clazz);
        });
    }

    public <T> T brpop(final String key, final int timeout, Class<T> clazz) {
        return (T) redisTemplate.execute((RedisCallback) redisConnection -> {
            redisConnection.select(DEFAULT_DB);
            List<byte[]> bs = redisConnection.bRPop(timeout, SafeEncoder.encode(key));
            if (bs == null) return null;
            return deserialize(bs.get(1), clazz);
        });
    }

    public Long llen(String key) {
        return (Long) redisTemplate.execute((RedisCallback) redisConnection -> {
            return redisConnection.lLen(SafeEncoder.encode(key));
        });
    }

    public static byte[] obj2bytes(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    public static Object bytes2obj(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    public RedisSerializer getSerializer() {

        if (this.serializer == null) {
            synchronized (this) {
                if (this.serializer == null) {
                    // 为了向下兼容默认,如果没有提供序列化器,默认使用,json序列化
                    serializer = new JsonSerializer();
//                    logger.info("RedisComponent [" + this.toString() + "] is done! serializer:" + serializer.toString());
                }
            }
        }

        return serializer;
    }

    public void setSerializer(RedisSerializer serializer) {
        this.serializer = serializer;
    }

    private byte[] serialize(Object object) {
        return getSerializer().serialize(object);
    }

    private <T> T deserialize(byte[] byteArray, Class<T> c) {
        return getSerializer().deserialize(byteArray, c);
    }

    private <E> List<E> deserializeForList(byte[] byteArray, Class<E> elementC) {
        return getSerializer().deserializeForList(byteArray, elementC);
    }

    public static interface SyncLockCallback<T> {
        public T callback();
    }

    private final static String SYNC_LOCK_SUFFIX = "_SYNC";

    /**
     * 同步保护
     *
     * @param lock
     * @param expire
     * @param callback
     * @return
     */
    public <T> T sync(final String lock, final long expire, SyncLockCallback<T> callback) {

        if (callback == null) {
            throw new IllegalArgumentException();
        }
        if (acquire(lock + SYNC_LOCK_SUFFIX, expire)) {
            try {
                return callback.callback();
            } finally {
                release(lock + SYNC_LOCK_SUFFIX);
            }
        } else {
            return null;
        }
    }

    /**
     * 本地同步保护
     *
     * @param lock
     * @param timeout
     * @param timeoutUnit
     * @param callback
     * @return
     */
    public <T> T syncWithLock(Lock lock, long timeout, TimeUnit timeoutUnit, SyncLockCallback<T> callback) {
        if (lock == null || timeoutUnit == null || callback == null) {
            throw new IllegalArgumentException();
        }
        try {
            if (lock.tryLock(timeout, timeoutUnit)) {
                try {
                    return callback.callback();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 通过SETNX试图获取一个锁
     *
     * @param key
     * @param expire 存活时间(秒)
     * @return
     */
    public boolean acquire(final String key, final long expire) {
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            boolean success = false;
            try {
                long value = System.currentTimeMillis() + expire * 1000 + 1;
                // 通过setnx获取一个lock
                success = redisConnection.setNX(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(value)));
                // setnx成功，则成功获取一个锁
                if (success) {
                    if (expire > 0) {
                        try {
                            redisConnection.expire(SafeEncoder.encode(key), expire);
                        } catch (Throwable e) {
                            logger.error("", e);
                        }
                    }
                    success = true;
                }
                // setnx失败，说明锁仍然被其他对象保持，检查其是否已经超时
                else {
                    // 当前锁过期时间
                    long oldValue = deserialize(redisConnection.get(SafeEncoder.encode(key)), Long.class);
                    // 超时
                    if (oldValue < System.currentTimeMillis()) {
                        // 查看是否有并发
                        Long oldValueAgain = deserialize(redisConnection.getSet(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(value))), Long.class);
                        // 获取锁成功
                        if (oldValueAgain == oldValue) {
                            if (expire > 0) {
                                try {
                                    redisConnection.expire(SafeEncoder.encode(key), expire);
                                } catch (Throwable e) {
                                    logger.error("", e);
                                }
                            }
                            success = true;
                        } else {
                            // 已被其他进程捷足先登了
                            success = false;
                        }
                    } else {
                        // 未超时，则直接返回失败
                        success = false;
                    }
                }
            } catch (Throwable e) {
                logger.error("", e);
            }
            return success;
        });
    }

    private final static Pattern LCK_TIME = Pattern.compile("\\d+");

    /**
     * 释放锁
     *
     * @param key
     */
    private void release(final String key) {
        redisTemplate.execute((RedisCallback) redisConnection -> {
            try {
                String lckUUID = deserialize(redisConnection.get(SafeEncoder.encode(key)), String.class);
                if (lckUUID == null || !LCK_TIME.matcher(lckUUID).find()) {
                    return null;
                }
                Long getValue = Long.parseLong(lckUUID);
                // 避免删除非自己获取得到的锁
                if (System.currentTimeMillis() < getValue.longValue()) {
                    redisConnection.del(SafeEncoder.encode(key));
                }
            } catch (Exception e) {
                logger.error("", e);
            }
            return null;
        });
    }
}


