package com.naiye.drm.component;

import com.google.common.base.Joiner;
import com.naiye.drm.cipher.MD5;
import com.naiye.drm.common.RedisConst;
import com.naiye.drm.component.redis.RedisComponent;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

@Component
public class MayiProxy {
    @Resource
    private RedisComponent redisComponent;

    private String getAuthHeader(String appkey, String secret, int useIndex, int releaseIndex) {
        // 创建参数表
        Map<String, String> paramMap = new TreeMap<String, String>();
        paramMap.put("app_key", appkey);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 使用中国时间，以免时区不同导致认证错误
        paramMap.put("timestamp", format.format(new Date()));
        if (useIndex > 0) {
            paramMap.put("with-transaction", "" + useIndex);
        }
        if (releaseIndex > 0) {
            paramMap.put("release-transaction", "" + useIndex);
        }
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(secret);
        for (String key : paramMap.keySet()) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(secret);
        String codes = stringBuilder.toString();
        // MD5编码并转为大写， 这里使用的是Apache codec
        String sign = MD5.toMD5(codes).toUpperCase();
        paramMap.put("sign", sign);
        // 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
        String authHeader = "MYH-AUTH-MD5 " + Joiner.on("&").withKeyValueSeparator("=").join(paramMap);
        return authHeader;
    }

//    private int getUseIndex(Long pid){
//        //获得今日使用代理次数
//        Long requestNum = redisComponent.incrby(RedisConst.PROXY_REQUEST_NUM + pid, 1L);
//        Integer proxyNO = requestNum.intValue() % 10;
//        return proxyNO;
//    }
//
//    private int releaseIndex(Integer useIndex, Long pid){
//        Long useNum = redisComponent.hincrby(RedisConst.MAYI_PROXY_KEY + useIndex, "1_" + pid, 1L);
//        Integer release = 0;
//        if (useNum == 1) {
//            redisComponent.expire(RedisConst.MAYI_PROXY_KEY + useIndex, 85 * 1000L);
//            release = useIndex;
//        } else if (useNum == 6) {
//            release = useIndex;
//        }
//        return release;
//    }

    /**
     * @param connection
     * @param field
     * @return 使用的代理编号
     */
    public int proxy(Connection connection, String field) {
        //获得今日使用代理次数
        Long requestNum = redisComponent.incrby(RedisConst.PROXY_REQUEST_NUM + new DateTime().toString("yyyyMMdd"), 1L);
        //获得使用代理编号
        Integer proxyNO = requestNum.intValue() % 10;
        //获得此代理的过期时间及使用次数
        Long useNum = redisComponent.hincrby(RedisConst.MAYI_PROXY_KEY + proxyNO.toString(), field, 1L);
        Integer release = 0;
        if (useNum == 1) {
            redisComponent.expire(RedisConst.MAYI_PROXY_KEY + proxyNO.toString(), 85 * 1000L);
            release = proxyNO;
        } else if (useNum == 6) {
            release = proxyNO;
        }
        //是否释放当前代理, 0为不释放，1为释放
        //一分半钟超时
        connection.proxy("s5.proxy.mayidaili.com", 8123).header("Proxy-Authorization", getAuthHeader("101361976", "94f1772c63b14a254d4804f33fba398e", proxyNO, release));
        return proxyNO;
    }
}

