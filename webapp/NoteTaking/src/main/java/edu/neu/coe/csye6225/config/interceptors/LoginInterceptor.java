package edu.neu.coe.csye6225.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired

    private AccountService accountService;
    private static final String SESSION_KEY = "SESSION_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        Object SESSION_OBJECT = session.getAttribute(SESSION_KEY);

        if(SESSION_OBJECT != null) { return true; }

        String authorization = request.getHeader("authorization");
        if(StringUtils.isNotEmpty(authorization)) {

            String authorizationString[] = authorization.split(" ");

            if(authorizationString.length == 2) {

                String base64String = authorizationString[1];
                String accountString = new String(Base64.decodeBase64(base64String), Charset.forName("UTF-8"));
                String account[] = accountString.split(":");

                if(account.length == 2) {
                    User user = new User();
                    user.setUsername(account[0]);
                    user.setPassword(account[1]);
                    if(accountService.logIn(user)){
                        session.setAttribute(SESSION_KEY, true);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//set date format
                        System.out.println(df.format(new Date()));// new Date()get current system time
                        return true;
                    }


                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
        response.setCharacterEncoding("UTF-8");
        response.setHeader("WWW-Authenticate", "Basic realm=\"STOP!\"");
        response.setHeader("Content-Type", "text/html");

        response.getWriter().print("<!DOCTYPE HTML><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body align=\"center\"><h3>You are not authorized！！！</h3></body></html>");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //DO NOTHING!!!
    }


}
