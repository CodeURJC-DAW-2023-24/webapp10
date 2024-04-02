package es.codeurj.mortez365.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class MyErrorController implements ErrorController {
//ErrorController is a Spring Boot interface that allows us to handle errors in a custom way.

    @Operation(summary = "Handle Error", description = "Handle error and display an error page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Error handled successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/error")
    public ModelAndView handleError(Model model) {
        model.addText("Oops Sorry !!!!");
        return new ModelAndView("404");
    }

}

