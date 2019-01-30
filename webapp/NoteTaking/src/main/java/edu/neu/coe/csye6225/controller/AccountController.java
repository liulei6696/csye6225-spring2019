package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.UserVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/register")
    public void register(@RequestBody User user) {
        accountService.signUp(user);
    }

    @GetMapping("/")
    public String getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String result = "";
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        if(user == null){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);

            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            return result;
        }
        if(accountService.logIn(user)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(new Date());
        }else {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            return result;
        }

    }
}
