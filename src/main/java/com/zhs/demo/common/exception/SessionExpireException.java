package com.zhs.demo.common.exception;

/**
 * Session 失效或到期时异常，发生该异常时，跳转到登陆页面
 */
public class SessionExpireException extends RuntimeException{

    public SessionExpireException(){}

    public SessionExpireException(String message){
        super(message);
    }

    public SessionExpireException(String message, Throwable cause){
        super(message, cause);
    }

    public SessionExpireException(Throwable cause){
        super(cause);
    }

    public SessionExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
