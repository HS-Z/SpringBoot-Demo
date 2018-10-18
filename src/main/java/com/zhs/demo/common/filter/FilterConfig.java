package com.zhs.demo.common.filter;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean registrationFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  //新建过滤器注册类
        filterRegistrationBean.setFilter(new UrlFilter());
        filterRegistrationBean.addUrlPatterns("/*");  //过滤规则
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setName("urlFilter");
        filterRegistrationBean.setOrder(1);  //过滤器执行顺序，越小，优先级越高
        return filterRegistrationBean;
    }
}
