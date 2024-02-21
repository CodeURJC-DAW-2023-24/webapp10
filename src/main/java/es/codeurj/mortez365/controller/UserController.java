package es.codeurj.mortez365.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import es.codeurj.mortez365.model.Match;
import es.codeurj.mortez365.repository.MatchRepository;




@Controller
public class UserController {

    @Autowired
    private MatchRepository matches;

    @RequestMapping("/mortez365")
    public String index() {
        return "index";
    }

   

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/wallet")
    public String wallet() {
        return "wallet";
    }


  
    @GetMapping("/roulette")
    public String roulette() {
        return "roulette";
    }

    @GetMapping("/slots")
    public String slots() {
        return "slots";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/responsablegame")
    public String responsablegame() {
        return "responsablegame";
    }
    
    @GetMapping("/bets")
    public String getMatches(Model model) {
    List<Match> allMatches = matches.findAll();
    model.addAttribute("matches", allMatches);
    return "bets"; 
}



}