package com.wrial.interceptor;
/*
 * @Author  Wrial
 * @Date Created in 15:11 2019/8/16
 * @Description 登录拦截器
 */

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    //已经在xml中配置为Bean了
    private List<String> unCheckUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //ContextPath(项目根路径)+RequestURI（从根路径开始到结尾，也就是相对路径）=requestURL
        String requestUrl = request.getRequestURI();
        requestUrl=requestUrl.replaceAll(request.getContextPath(), "");
        // 判断是否针对匿名路径需要拦截，如果包含，则表示匿名路径，需要拦截，否则通过拦截器
        /*
        在mvc中配置的bean
         <bean class="com.wrial.interceptor.LoginInterceptor">
               <property name="unCheckUrls">
                  <list>
					<value>/users/login.action</value>
                  </list>
              </property>
            </bean>
         */
        if (unCheckUrls.contains(requestUrl)){
            // 包含公开url，直接跳过
            return true;
        }

        if(null == request.getSession().getAttribute("sessionUser")){
            response.sendRedirect(request.getContextPath()+"/users/login.action");

            return false;
        }

        // 放行
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    public List<String> getUnCheckUrls() {
        return unCheckUrls;
    }

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }

}
