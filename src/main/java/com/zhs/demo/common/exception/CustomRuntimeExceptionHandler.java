package com.zhs.demo.common.exception;

import com.zhs.demo.utils.Json;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义运行时异常
 */
@ControllerAdvice
public class CustomRuntimeExceptionHandler{

    private static final Logger LOGGER= Logger.getLogger(CustomRuntimeExceptionHandler.class);


    @ExceptionHandler(CustomRuntimeException.class)
    @ResponseBody
    public Json handlerException(CustomRuntimeException exception) {
        LOGGER.error("系统发生异常：", exception);
        Json json = new Json();
        json.setSuccess(false);
        json.setMsg(exception.getMessage());
        return json;
    }


    /**
     * 发生该异常时，跳转到登陆页面
     * @param exception
     * @return
     */
    @ExceptionHandler(SessionExpireException.class)
    public String handlerException(SessionExpireException exception) {
        LOGGER.error("session失效或已过期：", exception);
        return "/login";
    }


 }
