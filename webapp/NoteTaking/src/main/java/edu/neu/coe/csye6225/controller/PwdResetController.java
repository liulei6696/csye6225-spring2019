package edu.neu.coe.csye6225.controller;

import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;

@RestController
public class PwdResetController {

    @RequestMapping(method = RequestMethod.GET, value = "/reset*")
    public ResponseEntity<String> passwordReset(HttpServletResponse httpServletResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "feature still in progress");
        httpServletResponse.setHeader("status", String.valueOf(SC_ACCEPTED));
        return ResponseEntity.accepted()
                .body(jsonObject.toString());
    }
}
