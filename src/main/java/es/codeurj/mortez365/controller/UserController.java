package es.codeurj.mortez365.controller;
import java.util.List;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Result;
import es.codeurj.mortez365.repository.BetRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;




@Controller
public class UserController {

    @Autowired
    private EventRepository events;

    @Autowired
    private BetRepository bets;

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
        assert event != null;
        model.addAttribute("event", event);
        model.addAttribute("feeT", 1.7);
        model.addAttribute("feeL", 3.5 - event.getFee());
        return "single-product";
    }
    @PostMapping("/single-product")
    public String generateBet(@RequestParam("bet-amount") Double money,  @RequestParam("eventId") Long eventId, Model model){
        Event event = events.findById(eventId).orElse(null);
        System.out.println(money);
        assert event != null;
        System.out.println(event.getName());
        bets.save(new Bet(event, money, Result.WIN));
        return "redirect:/index";
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

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }



}