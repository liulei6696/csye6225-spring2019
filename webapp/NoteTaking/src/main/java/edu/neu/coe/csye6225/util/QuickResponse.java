package edu.neu.coe.csye6225.util;

import org.springframework.http.ResponseEntity;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class QuickResponse {

    /**
     * static method, when user login failed
     * construct unauthorized message for controller
     *
     */
    public static ResponseEntity<String> userUnauthorized(@NotNull HttpServletResponse httpServletResponse) throws IOException {
        JSONObject resultJson = new JSONObject();
        resultJson.put("message", "Login failure! The username or password is wrong");
        httpServletResponse.setHeader("status", String.valueOf(SC_UNAUTHORIZED));
//        httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
        return ResponseEntity.badRequest()
                .body(resultJson.toString());
    }

    /**
     * when user don't have access to one resource
     *
     */
    public static ResponseEntity<String> userNoAccess(@NotNull HttpServletResponse httpServletResponse) throws IOException{
        JSONObject resultJson = new JSONObject();
        resultJson.put("message", "No access to resource");
        httpServletResponse.setHeader("status", String.valueOf(SC_UNAUTHORIZED));
//        httpServletResponse.sendError(SC_UNAUTHORIZED,"No access to resource");
        return ResponseEntity.badRequest()
                .body(resultJson.toString());
    }

    /**
     * when no file is uploaded
     *
     */
    public static ResponseEntity<String> noFile(@NotNull HttpServletResponse httpServletResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "No file");
        httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
        return ResponseEntity.badRequest()
                .body(jsonObject.toString());
    }

    /**
     * quick construct a response, return bad_request
     *
     */
    public static ResponseEntity<String> quickBadRequestConstruct(@NotNull HttpServletResponse httpServletResponse, String message) throws IOException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
//        httpServletResponse.sendError(SC_UNAUTHORIZED,message);
        return ResponseEntity.badRequest()
                .body(jsonObject.toString());
    }
}
