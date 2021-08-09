package cn.allms.framework.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.allms.common.constants.SessionConstants.USER_SESSION_KEY;

/**
 * 登录拦截器
 * @author allms
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //1. admin用户未登录
        if (request.getSession().getAttribute(USER_SESSION_KEY) == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
