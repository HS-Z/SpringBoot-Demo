package com.zhs.demo.shiro;

import com.zhs.demo.constant.RedisKey;
import com.zhs.demo.model.basic.UserInfo;
import com.zhs.demo.redis.RedisUtils;
import com.zhs.demo.service.basic.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 权限控制
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        if (principalCollection.getPrimaryPrincipal() == null) {
            return null;
        }

        String account= (String) principalCollection.getPrimaryPrincipal();  //登陆账号

        UserInfo userInfo=userInfoService.findByAccount(account);
        if (userInfo == null){
            return null;
        }

        //添加角色权限和控制
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }


    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        String account=(String) authenticationToken.getPrincipal();  //登陆账号

        UserInfo userInfo=userInfoService.findByAccount(account);

        if (userInfo == null){  //账号不存在
            throw new UnknownAccountException();
        }

        if (Boolean.TRUE.equals(userInfo.getLogOut())){  //账号已注销
            throw new UnknownAccountException();
        }

        /*
         *  记录用户在某段时间登陆的次数，访问一次，计数一次，不考虑密码是否正确
         *  目前只有在密码错误多次的情况下，该账号会被锁定，成功登陆后，会清除该用户的记录数，不妨碍下次登陆
         */

        if (redisUtils.exists(RedisKey.LOGIN_LOCK + account)){  //key值存在，说明该账号已被锁定
            throw new LockedAccountException();  //抛出账号锁定异常
        }

        redisUtils.increment(RedisKey.LOGIN_COUNT + account,1);    //账号未被锁定，每次访问记录增加 1

        if ( Long.valueOf(redisUtils.get(RedisKey.LOGIN_COUNT + account).toString()) > 5 ){  // 错误次数大于5次，则锁定该账号
            redisUtils.set(RedisKey.LOGIN_LOCK + account,"Locked",1,TimeUnit.HOURS);   //账号锁定一小时
            redisUtils.remove(RedisKey.LOGIN_COUNT + account);  //移除key
        }

        if (redisUtils.exists(RedisKey.LOGIN_LOCK + account)){  //key值存在，说明该账号已被锁定
            throw new LockedAccountException();  //抛出账号锁定异常
        }


        // 获取盐值
        ByteSource salt = ByteSource.Util.bytes(userInfo.getSecurity());

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(account, userInfo.getPassword(), salt, getName());

        return authenticationInfo;
    }
}
