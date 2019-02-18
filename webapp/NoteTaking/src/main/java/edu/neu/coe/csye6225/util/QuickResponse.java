package edu.neu.coe.csye6225.util;

import org.springframework.http.ResponseEntity;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class QuickResponse {

    /**
     * static method
     * construct unauthorized message for controller
     * @param httpServletResponse the http servlet response of controller
     * @return the response entity
     * @throws IOException the exception of sendError()
     */
    public static ResponseEntity<String> userUnauthorized(@NotNull HttpServletResponse httpServletResponse) throws IOException {
        JSONObject resultJson = new JSONObject();
        resultJson.put("message", "Login failure! The username or password is wrong");
        httpServletResponse.setHeader("status", String.valueOf(SC_UNAUTHORIZED));
        httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
        return ResponseEntity.badRequest()
                .body(resultJson.toString());
    }
}
