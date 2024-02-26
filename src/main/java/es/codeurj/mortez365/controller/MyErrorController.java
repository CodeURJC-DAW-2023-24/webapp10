package es.codeurj.mortez365.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

        @RestController
        public class MyErrorController implements ErrorController  {

            private static final String PATH = "/error";

            @RequestMapping(value = PATH)
            public String defaultErrorMessage() {
                return "A custom error has occurred in the application.";
            }

          
        }
    

