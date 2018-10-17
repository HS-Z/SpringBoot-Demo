package com.zhs.demo.controller.task;

import com.zhs.demo.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 */
@Component
public class Task {

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 一分钟执行一次
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void task(){
        System.out.println("定时任务启动");
    }


}
