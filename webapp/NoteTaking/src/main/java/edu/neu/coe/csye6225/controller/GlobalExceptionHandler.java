package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.util.QuickResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<String> handleNoFile (HttpServletResponse httpServletResponse, Throwable t) {
        return QuickResponse.noFile(httpServletResponse);
    }
}
