package com.yz.exception;

import com.yz.controller.AuthController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author andrew
 * @date 2020-11-04
 */
@Component
@RestControllerAdvice(basePackageClasses = {AuthController.class})
@Slf4j
public class ControllerExceptionHandler {
    
    @ExceptionHandler
    public String exception(Exception e) {
        log.error("server error : ", e);
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }
}

