package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/login")
    public void logIn(@RequestBody User user){
        accountService.logIn(user);
    }

    @GetMapping("test")
    public User getUser() {
        return accountService.getUser("karen","123");
    }
}
