package com.zhs.demo.exception;

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


 }
