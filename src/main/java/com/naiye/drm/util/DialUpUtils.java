package com.naiye.drm.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: jyl
 * @Descriptions:
 * @Date: create in  17:11 2018/3/5
 */
public class DialUpUtils {
    private static final Logger logger = LoggerFactory.getLogger(DialUpUtils.class);
    private static Lock lock = new ReentrantLock();
    private static AtomicInteger sucOrderNum = new AtomicInteger(0);

    /**
     * 连接宽带
     *
     * @param cname:连接名，比如“宽带连接”
     * @param uname:宽带连接的用户名
     * @param pwd:宽带连接的密码
     */
    public static void connect(String cname, String uname, String pwd) throws Exception {
        Process p = Runtime.getRuntime().exec("rasdial.exe " + cname + " " + uname + " " + pwd);
        Long startTime = System.currentTimeMillis();
        p.waitFor();
        Long endTime = System.currentTimeMillis();
        logger.info("connect cost time = {}", endTime - startTime);
    }

    public static void disconnect() throws Exception {
        Process p = Runtime.getRuntime().exec("rasdial.exe /DISCONNECT");
        p.waitFor();
    }

    public static int increment(){
        return sucOrderNum.incrementAndGet();
    }

    public static int getCount() {
        return sucOrderNum.get();
    }

    public static void reconnect(String cname, String uname, String pwd) throws Exception {
        if (lock.tryLock()) {
            try {
                disconnect();
                connect(cname, uname, pwd);
                sucOrderNum.set(0);
                logger.info("reconnect , ip={}", IpUtils.INTERNET_IP);
            } catch (Exception e) {
                logger.error("重连异常", e);
            } finally {
                lock.unlock();
            }
        }
    }
}
