package com.example.demo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class HttpErrorController implements ErrorController {

    private final MessageSource messageSource;

    @Autowired
    public HttpErrorController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping("/error")
    public String handleError(
            Locale locale,
            Model model,
            HttpServletRequest request,
            Exception ex
    ) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.valueOf(status.toString());

            Map<String, String> metaData = new HashMap<>();

            // 403
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "/errors/httperrors/403";

            }

            // 404
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/errors/httperrors/404";
            }

            // 405
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // do somthing
            }

            // 500
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // do somthing
            }

        }


        return "templates/errors/httperrors";
    }

    public String getErrorPath() {
        return "/error";
    }

}
