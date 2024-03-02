package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.List;


import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Result;
import es.codeurj.mortez365.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.ui.Model;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.UserSevice;




@Controller
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository events;


    @Autowired
    private BetRepository bets;

    @RequestMapping("/index")
    public String index() {
        log.info("Prueba123");
        log.info(userRepository.findByName("usuario_prueba").toString());
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
        model.addAttribute("feeL", Math.round((3.5 - event.getFee()) * 100.0)/ 100.0);
         return "single-product";
    }
    @PostMapping("/single-product")
    public String generateBet(@RequestParam("bet-amount") Double money,  @RequestParam("eventId") Long eventId,
                              @RequestParam("selected-bet") String selectedBet,Model model){
        Event event = events.findById(eventId).orElse(null);
        assert event != null;

        Double m = 0.0;
        Result result = null;
        switch (selectedBet){
            case "Victoria":
                result = Result.WIN;
                m = event.getFee();
                break;
            case "Empate":
                result = Result.TIE;
                m = 1.7;
                break;
            case "Derrota":
                result = Result.LOSE;
                m = (3.5 - event.getFee());
                break;
            default:
                break;
        }
        m = m * money;
        System.out.println(m);
        Double p = m - money;
        bets.save(new Bet(event, money, result, m, p));
        return "redirect:/index";
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
    public String showWallet(Model model, Principal principal) {
    // Get the current user
    Optional<User> user = userRepository.findByName(principal.getName());

    if (user.isPresent()) {
        // Get the balance
        double balance = user.get().getWallet().getMoney();

        // Add the balance to the model
        model.addAttribute("currentBalance", balance);
    }
        return "wallet";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("bets", bets.findAll(PageRequest.of(0, 9)).getContent());
        List<Bet> allBets = bets.findAll();
        Double totalWinningAmount = 0.0;
        Double totalBet = 0.0;
        Double totalBenefit = 0.0;
        for (Bet bet : allBets) {
            totalWinningAmount += bet.getWinning_amount();
            totalBet += bet.getBet_amount();
            totalBenefit = bet.getProfit();
        }
        model.addAttribute("total-bet", totalBet);
        model.addAttribute("total-winning-amount", totalWinningAmount);
        model.addAttribute("total-benefit", totalBenefit);
        return "cart";
    }

    @GetMapping("/betsadmin")
    public String betsadmin() {
        return "betsadmin";
    }





