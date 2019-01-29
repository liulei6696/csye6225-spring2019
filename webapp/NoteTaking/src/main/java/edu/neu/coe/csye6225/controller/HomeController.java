package edu.neu.coe.csye6225.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
