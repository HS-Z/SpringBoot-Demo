package com.zhs.demo.common.filter;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean registrationFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  //新建过滤器注册类
        registrationBean.setFilter(new UrlFilter());
        registrationBean.addUrlPatterns("/*");  //设置需要过滤的请求模式
        registrationBean.setName("UrlFilter");
        registrationBean.setOrder(1);  //过滤器执行顺序，越小，优先级越高
        return registrationBean;
    }
}
