package com.zhs.demo.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * 自定义错误拦截类
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "/error",method = {RequestMethod.POST,RequestMethod.GET})
    public String handleError(HttpServletRequest request){

        Integer statusCode=(Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode  == 401){
            return "error/401";
        }else if (statusCode  == 404){  //资源找不到
            return "error/404";
        }else if (statusCode  == 403){  //禁止访问
            return "error/403";
        }else if (statusCode  == 500){  //内部服务器错误
            return "error/500";
        }else {
            return "error/500";
        }
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }


}
