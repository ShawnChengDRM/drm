package com.naiye.drm.common;

/**
 * @Author: csf
 * @Descriptions: 响应码枚举，参考HTTP状态码的语义
 * @Date: create in  16:20 2018/6/25
 */
public enum ResultCode {

    SUCCESS("200"), //成功
    FAIL("400"), //失败
    OK("0"),//OK
    UNAUTHORIZED("401"), //未认证（签名错误）
    NOT_FOUND("404"), //接口不存在
    INTERNAL_SERVER_ERROR("500"); //服务器内部错误


    private String code;

    private ResultCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}