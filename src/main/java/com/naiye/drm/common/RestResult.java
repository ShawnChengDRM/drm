package com.naiye.drm.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author: csf
 * @Descriptions: 统一封装API返回信息
 * @Date: create in  16:15 2018/6/25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult {

    //状态码
    private String code;
    //消息
    private String message;
    //内容
    private Object data;

    public RestResult() {

    }

    public RestResult setCode(ResultCode code) {
        this.code = code.getCode();
        return this;
    }

    public RestResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public RestResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RestResult setData(Object data) {
        this.data = data;
        return this;
    }

}
