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
        registrationBean.addUrlPatterns("/*");  //过滤规则
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
        registrationBean.setName("UrlFilter");
        registrationBean.setOrder(1);  //过滤器执行顺序，越小，优先级越高
        return registrationBean;
    }
}
