package es.codeurj.mortez365.restController;
    
   

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularController {
    @GetMapping({"/new/**/{path:[^\\.]*}", "/{path:new[^\\.]*}"})
    public String redirect() {
        return "forward:/new/index.html";
    }
}
