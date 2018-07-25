package com.naiye.drm.common;

import com.naiye.drm.model.Test;

import java.util.Map;

/**
 * @Author: csf
 * @Descriptions: 线程变量 一般情况下只能一个线程访问
 * @Date: create in  10:35 2018/7/23
 */
public class RequestThread {
    public static final ThreadLocal<Test> threadLocal = new ThreadLocal<>();

    public static Test get() {
        if (threadLocal.get() == null) {
            threadLocal.set(new Test());
        }
        return threadLocal.get();
    }

    public static void set(Test test){
        threadLocal.set(test);
    }

    public static String getName(){
        return get().getName();
    }

    public static void setName(String name){
        get().setName(name);
    }

    public static String getPassword(){
        return get().getPassword();
    }

    public static void setPassword(String password){
        get().setPassword(password);
    }


}
