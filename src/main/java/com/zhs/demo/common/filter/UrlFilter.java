package com.zhs.demo.common.filter;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class UrlFilter implements Filter{


    //设置不需要被拦截的请求
    private String[] includeUrls = new String[]{"/login","/loginSystem","/register","/index"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取当前 request 中的session，如果 session 为null，不创建新的 session
        HttpSession session = request.getSession(false);

        filterChain.doFilter(servletRequest, servletResponse);

        String url = request.getRequestURI();

        boolean o=isNeedFilter(url);
        System.out.println("请求地址为："+url);
        System.out.println("是否被拦截："+o);



        /*String url = request.getRequestURI();

        boolean needFilter = isNeedFilter(url);  //判断当前请求是否需要过滤

        if (needFilter){

            if (session == null || session.getAttribute("sessionInfo") == null){
                throw new SessionExpireException();
            }

            if (url.startsWith("#")){   //如果当前请求路径以 # 开头，去除 # 后再访问
                url = url.substring(0);
                request.getRequestDispatcher(url).forward(servletRequest,servletResponse);

            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }


        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }*/
    }

    @Override
    public void destroy() {

    }


    /**
     * 判断请求地址是否需要拦截
     * @param url
     * @return
     */
    public boolean isNeedFilter(String url){

        if (StringUtils.isBlank(url)){
            return false;
        }

        //判断是否是静态资源
        if (url.contains(".js") || url.contains(".css") || url.contains(".")){
            return false;   //静态资源不需要被拦截
        }

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(url)) {
                return false;
            }
        }

        return true;
    }


}
