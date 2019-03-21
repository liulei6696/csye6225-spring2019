package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.*;
import edu.neu.coe.csye6225.util.QuickResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountValidation accountValidation;
    private final NoteService noteService;
    private final AttachmentService attachmentService;
    private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

    /**
     * changed field dependency injection to constructor injection
     * @param accountService autowired account service
     */
    @Autowired
    public AccountController (AccountService accountService, AccountValidation accountValidation,
                              NoteService noteService, AttachmentService attachmentService) {
        this.accountService = accountService;
        this.accountValidation = accountValidation;
        this.noteService = noteService;
        this.attachmentService = attachmentService;
    }

    /**
     * controller for user register "/user/register"
     * @param user request body in json
     * @param httpServletResponse response
     * @return response body in json
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user/register")
    public ResponseEntity<String> register(@RequestBody User user, HttpServletResponse httpServletResponse) {
        accountService.createTable(); noteService.createNew(); attachmentService.createNew();
        statsd.incrementCounter("endpoint.userRegister.http.post");
        // validate username
        if(!accountValidation.nameValidation(user.getUsername())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "register failed");
            jsonObject.put("cause", "username not valid");
            httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        }

        // validate password
        else if(!accountValidation.isPasswordStrong(user.getPassword())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "register failed");
            jsonObject.put("cause", "password not strong enough");
            httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        }

        // validate if user already registered
        else if(accountValidation.isUserRegistered(user)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "register failed");
            jsonObject.put("cause", "user already registered");
            httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        }

        // sign up into database
        else if(accountService.signUp(user)){
            JSONObject jsonObject = new JSONObject();
            httpServletResponse.setHeader("status", String.valueOf(HttpStatus.OK));
            jsonObject.put("message", "register success");
            return ResponseEntity.ok()
                    .body(jsonObject.toString());
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "register failed!");
            jsonObject.put("cause", "unkown");
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        }
    }


    /**
     * controller for mapping "/"
     * @param httpServletRequest verification info
     * @param httpServletResponse response message
     * @return current date if username and password is correct
     * @throws IOException by sendError()
     */
    @GetMapping("/")
    public ResponseEntity<String> getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        accountService.createTable();
        statsd.incrementCounter("endpoint.homepage.http.get");
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        if(user == null){
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if(accountService.logIn(user)){
            JSONObject jsonObject = new JSONObject();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            httpServletResponse.setHeader("status", String.valueOf(SC_OK));
            jsonObject.put("date", df.format(new Date()));
            return ResponseEntity.ok()
                    .body(jsonObject.toString());
        }else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }

    }
}
