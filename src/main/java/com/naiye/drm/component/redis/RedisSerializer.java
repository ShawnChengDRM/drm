package com.naiye.drm.component.redis;

import java.nio.charset.Charset;
import java.util.List;

/**
 * redis序列化
 */
interface RedisSerializer {

    public final static Charset CHARSET = Charset.forName("utf-8");

    byte[] serialize(Object object);

    <T> T deserialize(byte[] byteArray, Class<T> clazz);

    <E> List<E> deserializeForList(byte[] byteArray, Class<E> itemClazz);

}