package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.EventSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

import jdk.jfr.Timespan;
import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;
import java.util.stream.Collectors;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Result;
import es.codeurj.mortez365.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@EnableScheduling
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
     List<Event> notFinalizedEvents = eventService.filterFinalizedEvents(eventsPage.getContent());
     System.out.println(eventsPage);
     System.out.println(notFinalizedEvents);

    model.addAttribute("events", notFinalizedEvents);
    model.addAttribute("currentPage", eventsPage.getNumber());
    model.addAttribute("totalPages", eventsPage.getTotalPages());
    model.addAttribute("totalItems", eventsPage.getTotalElements());

    return "bets";
    }

    @GetMapping("/events/{id}/image")
	public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {

		Optional<Event> event = eventService.findById(id);

		if (event.isPresent() && event.get().getImage() != null) {

			Resource file = new InputStreamResource(event.get().getImage().getBinaryStream());

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.contentLength(event.get().getImage().length()).body(file);

		} else {
			return ResponseEntity.notFound().build();
		}
	}

    @Operation(summary = "Get Events JSON", description = "Retrieve a list of events in JSON format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of events"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bets/json")
    @ResponseBody
    public List<Event> getEventsJson(@RequestParam(name = "start", defaultValue = "0") int start,
                                     @RequestParam(name = "count", defaultValue = "9") int count) {
        return events.findAll(PageRequest.of(start, count)).getContent();
    }

    @Operation(summary = "Get Single Product", description = "Retrieve details of a single product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of product details"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/single-product")
    public String getSingleProduct(@RequestParam("id") Long id, Model model) {
        Event event = events.findById(id).orElse(null);
        assert event != null;
        model.addAttribute("event", event);
        model.addAttribute("feeT", 1.7);
        model.addAttribute("feeL", Math.round((3.5 - event.getFee()) * 100.0)/ 100.0);
        return "single-product";
    }


    @Operation(summary = "Generate Bet", description = "Generate a bet based on user input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bet successfully generated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
            Bet bet = new Bet(event, money, result, m, p, currentUser);
            System.out.println(bet.getEvent());
            betRepository.save(bet);
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

    @Scheduled(fixedRate = 10000)
    public void processEvents(){
        List<Event> listEvents = events.findAll();
        Date currentDate = new Date();

        for(Event e: listEvents){
            if(!e.getFinished() && currentDate.after(e.getDeadline())){
                Result result = generateRandomResult();
                System.out.println(e.getName() + result);
                List<Bet> bets = betRepository.findAll();
                List<Bet> eventBets = new ArrayList<>();
                for(Bet b: bets){
                    System.out.println(b.getEvent());
                    System.out.println(e);
                    if(b.getEvent().getName().equals(e.getName())){
                        eventBets.add(b);
                    }
                }
                //List<Bet> bets = betRepository.findByEvent(e);   NOT WORKING

                e.setFinished(true);
                String[] equipos = e.getName().split("-");
                if(result == Result.WIN){
                    e.setWinner_team(equipos[0].trim());
                    e.setLoser_team(equipos[1].trim());
                } else if (result == Result.LOSE) {
                    e.setWinner_team(equipos[1].trim());
                    e.setLoser_team(equipos[0].trim());
                } else{
                    e.setWinner_team("empate");
                    e.setLoser_team("empate");
                }
                e.setFinalResult(result);

                System.out.println("ACA ESTAMOS");

                for(Bet b: eventBets){
                    if(b.getResult() == result){
                        System.out.println("LLEGA");
                        User user = userRepository.findByUsername(b.getUser().getName()).orElseThrow();
                        user.getWallet().addMoney(b.getProfit());
                        userRepository.save(user);
                    } else {
                        System.out.println("PITON");
                        System.out.println(b.getEvent().getName());
                        b.setWinning_amount(0);
                        System.out.println(b.getWinning_amount());
                        betRepository.save(b);
                    }
                }
                events.save(e);
            }
        }
    }
    private Result generateRandomResult() {
        Result[] results = Result.values();
        Random random = new Random();
        int index = random.nextInt(results.length);
        return results[index];
    }

    @GetMapping("/cart")
    public String cart(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> currentUser = userRepository.findByUsername(authentication.getName());

        // Get the bets of the current user
        Page<Bet> betPage = betRepository.findByUser(currentUser, PageRequest.of(0, 9));
        List<Bet> bets = betPage.getContent();

        List<Bet> betsFinished = bets.stream()
                .filter(bet -> bet.getEvent().getFinished())
                .toList();

        List<Bet> betsInProgress = bets.stream()
                .filter(bet -> !bet.getEvent().getFinished())
                .toList();

        model.addAttribute("bets", betsInProgress);
        model.addAttribute("hasBets", !betsInProgress.isEmpty());

        model.addAttribute("betsFinished", betsFinished);
        model.addAttribute("hasBetsFinished", !betsFinished.isEmpty());
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

    @Operation(summary = "Add Event", description = "Add a new event to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully added"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String championship, @RequestParam String sport,
                           @RequestParam MultipartFile image, @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") @RequestParam Date deadline) throws IOException {
        Event event = new Event();
        //Save the image in the data base
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
            event.setDeadline(deadline);
            event.setImageFile(("assets/img/laliga/"+image.getOriginalFilename()));
            eventService.save(event);
        }
        return "redirect:/betsadmin";
    }

    @Operation(summary = "Delete Event", description = "Delete an event from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Edit Event", description = "Edit details of an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event details successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get User's Wallet Value", description = "Get the value of the user's wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's wallet value successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getValue")
    @ResponseBody
    public int getValue(Model model, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(name).orElseThrow();
        int valor =  (int) user.getWallet().getMoney();
        return valor;
    }

    @Operation(summary = "Update User's Wallet Value", description = "Update the value of the user's wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's wallet value successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/updateValue")
    @ResponseBody
    public String updateValue(@RequestBody Integer newBankValue, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        log.info("UPDATE VALUE LLEGA HASTA AQUI");
        User user = userRepository.findByUsername(name).orElseThrow();
        user.getWallet().setMoney(newBankValue);
        userRepository.save(user);
        return "/roulette";
    }


    @GetMapping("/user/{id}/image")
    public String getUserImage(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        byte[] image = user.getImage();

        String imageBase64 = Base64.getEncoder().encodeToString(image);
        return imageBase64;
    }

}

    

