package es.codeurj.mortez365.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyErrorController implements ErrorController {
//ErrorController is a Spring Boot interface that allows us to handle errors in a custom way.
    @GetMapping("/error")
    public ModelAndView handleError(Model model) {
        model.addText("Oops Sorry !!!!");
        return new ModelAndView("404");
    }

}

