package com.zhs.demo.utils;

import com.zhs.demo.model.session.SessionInfo;
import com.zhs.demo.model.basic.UserInfo;
import com.zhs.demo.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Service
public final class SessionUtils implements Serializable {

    @Autowired
    private RedisUtils redisUtils;


    public SessionUtils(RedisUtils redisUtils) {
        if (redisUtils == null)
            throw new RuntimeException(" 未找到 Redis 缓存工具类");
        this.redisUtils = redisUtils;
    }


    public SessionInfo setSessionInfo(UserInfo userInfo) {

        if (userInfo == null){
            throw new RuntimeException("获取账号信息失败");
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();

        if (request == null) {
            throw new RuntimeException("获取 request 失败");
        }

        if (session == null) {
            throw new RuntimeException("获取 session 失败");
        }

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(userInfo.getId());
        sessionInfo.setUserName(userInfo.getUserName());
        sessionInfo.setUserType(userInfo.getUserType());
        sessionInfo.setAccount(userInfo.getAccount());

        session.setAttribute("sessionInfo",sessionInfo);

        return sessionInfo;
    }


    public SessionInfo getSessionInfo() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();

        if (request == null) {
            throw new RuntimeException("获取 request 失败");
        }

        if (session == null) {
            throw new RuntimeException("获取 session 失败");
        }

        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");

        return sessionInfo;
    }

}
