package com.zhs.demo.constant;

/**
 *  定义 Redis 的常用 key
 */
public class  RedisKey {

    /*
     * 用户登陆时，记录用户的登陆次数，主要是记录连续登陆的错误次数
     * key 的格式为 LOGIN_COUNT + account
     */
    public static final String LOGIN_COUNT = "LOGIN_COUNT";


    /*
     * 当错误登陆记录数到达某一值后，用户的账号将会被锁定
     * 判断key是否存在，存在则说明该账号已被锁定，不能登陆
     * key 的格式为 LOGIN_LOCK + account
     */
    public static final String LOGIN_LOCK = "LOGIN_LOCK";

}
