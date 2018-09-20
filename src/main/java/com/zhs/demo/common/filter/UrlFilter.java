package com.zhs.demo.common.filter;

import com.zhs.demo.common.exception.SessionExpireException;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class UrlFilter implements Filter{


    String[] includeUrls = new String[]{"/login","/loginSystem","/register"};   //设置不被拦截的请求


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("请求路径为："+request.getRequestURI());

        //获取当前 request 中的session，如果 session 为null，不创建新的 session
        HttpSession session = request.getSession(false);

        filterChain.doFilter(servletRequest, servletResponse);



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
     * 判断当前请求是否需要拦截
     * @param uri
     * @return
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }
        return true;
    }


}
