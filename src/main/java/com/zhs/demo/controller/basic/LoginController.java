package com.zhs.demo.controller.basic;

import com.zhs.demo.constant.RedisKey;
import com.zhs.demo.model.basic.*;
import com.zhs.demo.model.session.SessionInfo;
import com.zhs.demo.common.redis.RedisUtils;
import com.zhs.demo.service.basic.*;
import com.zhs.demo.utils.Json;
import com.zhs.demo.utils.SessionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    private final Logger logger=LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleInfoService userRoleInfoService;
    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private RoleMenuInfoService roleMenuInfoService;
    @Autowired
    private MenuInfoService menuInfoService;


    /**
     * 拦截非法请求，跳转到登陆页面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){

        return "login";
    }


    @RequestMapping(value = "loginSystem",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Json loginSystem(@RequestParam("account") String account, @RequestParam("password") String password){

        Json json=new Json();

        Subject subject = SecurityUtils.getSubject();

        // 将用户名及密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);

        try {
            subject.login(token);
            // 判断当前用户是否登录
            if (subject.isAuthenticated()) {  //登陆成功

                redisUtils.remove(RedisKey.LOGIN_LOCK + account);   //登陆成功，清除锁定标记
                redisUtils.remove(RedisKey.LOGIN_COUNT + account);  //登陆成功，清除错误记录数

                UserInfo userInfo=userInfoService.findByAccount(account);  //根据账号查询用户信息
                sessionUtils.setSessionInfo(userInfo);  //将用户信息保存到session


                json.setSuccess(true);
                return json;
            }

        } catch (LockedAccountException e){  // 账号被锁定
            Long min=redisUtils.getExpire(RedisKey.LOGIN_LOCK + account) / 60 ;  // 账号锁定失效时间，单位为秒
            json.setSuccess(false);
            json.setMsg("该账号已被锁定，请"+ min +"分钟后再尝试登陆");
        } catch (UnknownAccountException e) {  // 账号不存在
            json.setSuccess(false);
            json.setMsg("用户名/密码错误");
        } catch (IncorrectCredentialsException e){  // 密码错误
            json.setSuccess(false);
            json.setMsg("用户名/密码错误");
        } catch (ExcessiveAttemptsException e){  // 登陆失败次数过多
            json.setSuccess(false);
            json.setMsg("登录失败多次，账户锁定1小时");
        } catch (AuthenticationException e) {
            json.setSuccess(false);
            json.setMsg("登陆失败");
        }
        return json;
    }


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){
        logger.info("用户登陆成功");
        SessionInfo sessionInfo=sessionUtils.getSessionInfo();
        if (sessionInfo == null){
            return "login";
        }

        Long userId=sessionInfo.getUserId();  //当前登陆用户id
        if (userId == null){
            return "login";
        }
        UserInfo userInfo = userInfoService.findById(userId);
        if (userInfo == null){
            return "login";
        }

        /*//根据用户查询角色信息（限制一个用户只能属于一个角色）
        UserRoleInfo userRoleInfo=userRoleInfoService.findUserRoleInfoByUserId(userId);
        if (userRoleInfo == null){
            throw new RuntimeException("该用户无角色信息");
        }

        //查询角色信息
        RoleInfo roleInfo=roleInfoService.findRoleInfoById(userRoleInfo.getRoleId());
        if (roleInfo == null){
            throw new RuntimeException("查询角色失败");
        }

        //查询权限菜单对应关系
        List<RoleMenuInfo> roleMenuInfoList=roleMenuInfoService.findRoleMenuListByRoleId(roleInfo.getId());
        if (roleMenuInfoList == null || roleMenuInfoList.size() == 0){
            throw new RuntimeException("该角色尚未分配菜单权限");
        }

        //查询菜单
        List<MenuInfo> menuInfoList = new ArrayList<>();
        for (RoleMenuInfo roleMenuInfo:roleMenuInfoList){
            MenuInfo menuInfo = menuInfoService.findMenuInfoById(roleMenuInfo.getMenuId());
            if (menuInfo != null){
                menuInfoList.add(menuInfo);
            }
        }

        model.addAttribute("menuInfoList",menuInfoList);*/

        return "index";
    }


}
