package com.naiye.drm.util;

import com.naiye.drm.common.RestResult;
import com.naiye.drm.common.ResultCode;
import org.springframework.stereotype.Component;

/**
 *
 * @Author: csf
 *
 * @Descriptions:
 * 工厂模式
 * 接口信息生成工具
 * 使用@Component 添加到Spring组件中
 *
 * @Date: create in  16:15 2018/6/25
 *
 *
 */
@Component
public class ResultGenerator {

    private static final String SUCCESS = "success";
    //成功
    public RestResult getSuccessResult() {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS);
    }
    //成功，自定义消息
    public RestResult getSuccessResult(String message) {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message);
    }
    //成功，附带额外数据
    public RestResult getSuccessResult(Object data) {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }
    //成功，自定义消息及数据
    public  RestResult getSuccessResult(String message,Object data) {
        return new RestResult()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
                .setData(data);
    }
    //失败，附带消息
    public  RestResult getFailResult(String message) {
        return new RestResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    //失败，自定义消息及数据
    public RestResult getFailResult(String message, Object data) {
        return new RestResult()
                .setCode(ResultCode.FAIL)
                .setMessage(message)
                .setData(data);
    }
    //自定义创建
    public RestResult getFreeResult(ResultCode code, String message, Object data) {
        return new RestResult()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }    //自定义创建
    public RestResult getFreeResultTwo(ResultCode code, String message) {
        return new RestResult()
                .setCode(code)
                .setMessage(message);
    }
}

