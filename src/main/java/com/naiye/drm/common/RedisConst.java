package com.naiye.drm.common;

/**
 * @Author: csf
 * @Descriptions: Redis的常量
 * @Date: create in  9:35 2018/6/26
 */
public enum RedisConst {

    // 因为已经定义了带参数的构造器，所以在列出枚举值时必须传入对应的参数
    CRAW_IP_LOCK("craw_ip_lock"),
    APP_PROXY_KEY("app_proxy_key:"),
    APP_MAX_PROXY_ID("app_max_proxy_id:"),
    DB_PROXY_LOCK("db_proxy_lock:"),
    MAYI_PROXY_KEY("mayi_proxy_key:"),
    PROXY_REQUEST_NUM("proxy_request_num:"),
    PROXY_TYPE("proxy_type"),

    /**
     * 代理编号
     */
    PROXY_NO("proxy_no_");


    // 定义一个 private 修饰的实例变量
    private String data;

    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
    private RedisConst(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    // 重写 toString() 方法
    @Override
    public String toString(){
        return this.data;
    }
}
