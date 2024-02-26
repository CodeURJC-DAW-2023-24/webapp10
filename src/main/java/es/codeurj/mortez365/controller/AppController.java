package es.codeurj.mortez365.controller;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;




@Controller
public class AppController {

    @Autowired
    private EventRepository events;

    @RequestMapping("/index")
    public String index() {
        return "index";
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
    public String getFilteredEvents(@RequestParam(name = "category", required = false) String category, Model model) {
        List<Event> filteredEvents;
    
        if ("Todos".equals(category)) {
          
            filteredEvents = events.findAll();
        } else if (category != null) {
          
            filteredEvents = events.findByChampionship(category);
        } else {
            
            filteredEvents = events.findAll(PageRequest.of(0, 9)).getContent();
        }
    
        model.addAttribute("events", filteredEvents);
        return "bets";
    }

    @GetMapping("/bets/json")
    @ResponseBody
    public List<Event> getEventsJson(@RequestParam(name = "start", defaultValue = "0") int start,
                                     @RequestParam(name = "count", defaultValue = "9") int count) {
        return events.findAll(PageRequest.of(start, count)).getContent();
    }
    

    @GetMapping("/single-product")
    public String getSingleProduct(@RequestParam("id") Long id, Model model) {
        Event event = events.findById(id).orElse(null);
        model.addAttribute("event", event);
        return "single-product";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/wallet")
    public String wallet() {
        return "wallet";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    



}