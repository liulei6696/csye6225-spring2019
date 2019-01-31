package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.UserVerification;
import edu.neu.coe.csye6225.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> register(@RequestBody User user) {
        ResultJson resultJson = new ResultJson();
        if(accountService.signUp(user)){
            resultJson.put("status", String.valueOf(HttpStatus.OK));
            resultJson.put("message", "register success");
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
            resultJson.put("message", "register failed!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        ResultJson resultJson = new ResultJson();
        if(user == null){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);

            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");

            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());

        }
        if(accountService.logIn(user)){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            resultJson.put("status", String.valueOf(HttpStatus.CREATED));
            resultJson.put("date", df.format(new Date()));
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }

    }
}
