package com.naiye.drm.interceptor;

import com.naiye.drm.common.RestResult;
//import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理器
 */
@ControllerAdvice
//@Slf4j
public class HandlerException {

    private static final Logger logger = LoggerFactory.getLogger(HandlerException.class);

    /**
     * 处理未知异常
     * @param e
     * @return
     */

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public RestResult unknowException(Exception e){
        e.printStackTrace();
        RestResult restResult =new RestResult();
        restResult.setCode("500");
        restResult.setMessage("系统出现未知异常");
        /**
         * 未知异常的话，这里写逻辑，发邮件，发短信都可以、、
         */
        return restResult;
    }

    /**
     * 处理已知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = CheckException.class)
    @ResponseBody
    public RestResult handlerCheckException(CheckException e){
        logger.info("发生了已知错误："+e.getMessage());
        RestResult resultBean =new RestResult();
        resultBean.setCode("400");
        resultBean.setMessage(e.getMessage());
        return resultBean;
    }

}
