package com.cf.taptap.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println(uri);
        if ("/userLogin".equals(uri) || "/userRegister".equals(uri)) {
            System.out.println(uri);
            return true;
        }
        //重定向
        Object object = request.getSession().getAttribute("user");
        if (object == null) {
            response.sendError(401, "请先登录");
        }
        return true;
    }
}
