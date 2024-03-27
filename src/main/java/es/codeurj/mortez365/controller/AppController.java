package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.EventSevice;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Result;
import es.codeurj.mortez365.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventSevice eventService;

    @Autowired
    private EventRepository events;

    @Autowired
    private BetRepository betRepository;

    @RequestMapping("/index")
    public String index(Model model) {
        // Get the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(currentUserName);

        // Add the current user to the model
        model.addAttribute("currentUser", true);
        if (currentUser.isEmpty()){
            model.addAttribute("currentUser", false);
        }

        return "index";
    }
    @GetMapping("/index")
    public String indx(Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userRepository.findByUsername(currentUserName);


        model.addAttribute("currentUser", false);
        if (currentUser.isEmpty()){
            model.addAttribute("currentUser", true);
        }

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

    @GetMapping("/games")
    public String games() {
        return "games";
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
    //Method to get the events
    @GetMapping("/bets")
    public String getFilteredEvents(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                Model model) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Event> eventsPage;

   
     eventsPage = events.findAll(pageable);
    

    model.addAttribute("events", eventsPage.getContent());
    model.addAttribute("currentPage", eventsPage.getNumber());
    model.addAttribute("totalPages", eventsPage.getTotalPages());
    model.addAttribute("totalItems", eventsPage.getTotalElements());

    return "bets";
}
    //Method to get the events in json format
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
    public String generateBet(@RequestParam("bet-amount") Double money, @RequestParam("eventId") Long eventId,
                              @RequestParam("selected-bet") String selectedBet,
                              RedirectAttributes redirectAttributes, Model model, HttpServletRequest request){
        Event event = events.findById(eventId).orElse(null);
        assert event != null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        log.info("NOMBRE ACTUAL: " + currentUserName);

        User currentUser = userRepository.findByUsername(currentUserName).orElseThrow();
        
        if(currentUser.getWallet().getMoney() > money){
            // Generate the bet
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
            double userMoney = currentUser.getWallet().getMoney();
            userMoney = userMoney - money;
            currentUser.getWallet().setMoney(userMoney);
            System.out.println(m);
            Double p = m - money;
            betRepository.save(new Bet(event, money, result, m, p, currentUser));
            return "redirect:/index";
        }
        else{
            String refer = request.getHeader("Referer");
            redirectAttributes.addFlashAttribute("noMoney", true);
            return "redirect:" + refer;
        }
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
  
    @GetMapping("/wallet")
    public String wallet (Model model, HttpServletRequest request){
        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(name).orElseThrow();
        model.addAttribute("user", user);
        return "wallet";
       
    }
    @PostMapping("/wallet/addFunds")
    public String increaseWallet(Model model, @RequestParam("amount") Long amount) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName()).orElse(null);

        if (currentUser != null) {
            log.info(currentUser.getName());
            log.info(String.valueOf(currentUser.getWallet().getMoney()));
            double money = currentUser.getWallet().getMoney();
            money = amount + money;
            log.info(String.valueOf(money));
            currentUser.getWallet().setMoney(money);
            userRepository.save(currentUser);
            log.info("DINERO", String.valueOf(currentUser.getWallet().getMoney()));
        }

        return "redirect:/wallet";
    }


    @GetMapping("/cart")
    public String cart(Model model) {
    
        log.info("AQUI EL CARRITO:");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userRepository.findByUsername(authentication.getName());

        if (currentUser.isPresent()) {
            log.info("NOMBRE DEL USUARIO ACTUAL: " + currentUser.get().getName());
        }else {
            log.info("EL USUARIO ACTUAL ESTA A NULL");
        }
        // Get the bets of the current user
        Page<Bet> betPage = betRepository.findByUser(currentUser, PageRequest.of(0, 9));
        List<Bet> bets = betPage.getContent();

        model.addAttribute("bets", bets);
        // Calculate the total bet, the total winning amount and the total benefit

        Double totalWinningAmount = 0.0;
        Double totalBet = 0.0;
        Double totalBenefit = 0.0;
        for (Bet bet : bets) {
            totalWinningAmount += bet.getWinning_amount();
            totalBet += bet.getBet_amount();
            totalBenefit = bet.getProfit();
        }
        // Add the total bet, the total winning amount and the total benefit to the model
        model.addAttribute("total-bet", totalBet);
        model.addAttribute("total-winning-amount", totalWinningAmount);
        model.addAttribute("total-benefit", totalBenefit);
        return "cart";
    }

   @GetMapping("/betsadmin")
    public String betsadmin(Model model) {
    List<Event> allEvents = events.findAll();
    model.addAttribute("events", allEvents);
    return "betsadmin";
}

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        try {
            // If the user is logged in, we get the user from the database
            String name = request.getUserPrincipal().getName();
            log.info("USER NAME: " + name);
            User user = userRepository.findByUsername(name).orElseThrow();
            byte[] imageBytes = user.getImage();
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", imageBase64);
            model.addAttribute("user", user);
        } catch (Exception e) {
            // If the user is not logged in, redirect to the login page
            return "redirect:/login";
        }
        return "profile";
    }

    @GetMapping("/loginerror")
    public String loginError() {
        return "loginerror";
    }

   @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String championship,
                           @RequestParam String sport, @RequestParam MultipartFile image) throws IOException {
        Event event = new Event();
        //Save the image int the data base
        if(!image.isEmpty()) {
            Path imageDirectory = Paths.get("src/main/resources/assets/img/laliga");
            String imagePath = imageDirectory.toFile().getAbsolutePath();
            Random rand = new Random();
            double randomValue = 1 + (2.5 - 1) * rand.nextDouble();
            byte[] bytes = image.getBytes();
            Path path = Paths.get(imagePath + "//" +image.getOriginalFilename());
            Files.write(path, bytes);
            event.setName(name);
            event.setChampionship(championship);
            event.setSport(sport);
            event.setFee(randomValue);
            event.setImage(("assets/img/laliga/"+image.getOriginalFilename()));
            eventService.save(event);


        }
        return "redirect:/betsadmin";
    }

    @PostMapping("/deleteEvent")
    public String deleteItem(@RequestParam("id") Long id, Model model) {
        events.deleteById(id);

        return "redirect:/betsadmin";
    }
    @GetMapping("/edit")
    public String editEvent(@RequestParam("id") Long id, Model model){
        Event event = events.findById(id).orElse(null);
        assert event != null;
        model.addAttribute("event", event);
        return "edit";
    }
    @PostMapping("/edit")
    public String edit(Model model, @RequestParam("eventId") Long eventId, @RequestParam("event-name") String eventName,
                       @RequestParam("event-championship") String eventChampionship, @RequestParam("event-sport") String eventSport,
                       @RequestParam("event-fee") Double eventFee) {
        Event event = events.findById(eventId).orElse(null);
        assert event != null;
        event.setName(eventName);
        event.setChampionship(eventChampionship);
        event.setSport(eventSport);
        event.setFee(eventFee);
        events.save(event);
        return "redirect:/betsadmin";
    }

    //Method to get the value of the money of the user
    @GetMapping("/getValue")
    @ResponseBody
    public int getValue(Model model, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(name).orElseThrow();
        int valor =  (int) user.getWallet().getMoney();
        return valor;
    }
//Method to update the value of the money of the user
@PostMapping("/updateValue")
@ResponseBody
public void updateValue(@RequestBody Map<String, Integer> payload, HttpServletRequest request) {
    String name = request.getUserPrincipal().getName();
    User user = userRepository.findByUsername(name).orElseThrow();
    user.getWallet().setMoney(payload.get("value"));
    userRepository.save(user);

}


@GetMapping("/user/{id}/image")
public String getUserImage(@PathVariable Long id) {
    User user = userRepository.findById(id).orElseThrow();
    byte[] image = user.getImage();
    
    String imageBase64 = Base64.getEncoder().encodeToString(image);
    return imageBase64;
}

}

    

