package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/")
public class HomeController {



    @RequestMapping(value = "message", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<String> home(){
        ArrayList<String> response = new ArrayList<>();
        response.add("hello");
        response.add("world");
        return response;
    }





}
