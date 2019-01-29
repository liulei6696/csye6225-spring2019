package edu.neu.coe.csye6225.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;

public class SecurityInterceptor implements HandlerInterceptor {
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

                    String password = "test";

                    AccountService accountService = (AccountService) SpringApplicationContext.getBean("AccountService");

                    Account account = accountService.getAccount();



                    if(StringUtils.equals("test", account[0]) && StringUtils.equals(password, account[1])) {

                        session.setAttribute(SESSION_KEY, true);

                        return true;
                    }
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401
        response.setCharacterEncoding("UTF-8");
        response.setHeader("WWW-Authenticate", "Basic realm=\"STOP!\"");
        response.setHeader("Content-Type", "text/html");

        response.getWriter().print("<!DOCTYPE HTML><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body align=\"center\"><h3>没有权限，慎入！！！</h3></body></html>");

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
