package es.codeurj.mortez365.restController;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.Result;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.BetService;


import es.codeurj.mortez365.service.EventService;
import es.codeurj.mortez365.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/api/bets")
public class BetRestController {

    @Autowired
    private BetService betService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Collection<Bet>> getBets() {
        Collection<Bet> bets = betService.findAllBets();
        return new ResponseEntity<>(bets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bet> getBetById(@PathVariable long id){
        Optional<Bet> bet = betService.findBetById(id);
        return bet.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<Bet>> getBetsByUser(@PathVariable long id){
        if(userRepository.existsById(id)){
            return new ResponseEntity<>(betService.findBetsByUser(id), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/event/{idE}")
    public ResponseEntity<Bet> createBet(@RequestBody Bet bet, @PathVariable Long idE) {
        Double fee;
        Event event;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userService.findByUsername(currentUserName);

        if(eventService.findById(idE).isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            event = eventService.findById(idE).get();
            bet.setEvent(event);
            if(eventService.findById(bet.getEvent().getId()).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        if(bet.getBet_amount() == null){
            return ResponseEntity.badRequest().build();
        }
        if(bet.getResult() == null){
            return ResponseEntity.badRequest().build();
        }
        if(bet.getResult() == Result.WIN){
            fee = event.getFee();
        } else if(bet.getResult() == Result.TIE){
            fee = 1.7;
        } else {
            fee = 3.5 - event.getFee();
        }
        bet.setFee(fee);
        bet.setWinning_amount(fee * bet.getBet_amount());
        bet.setProfit(bet.getWinning_amount() - bet.getBet_amount());

        if(currentUser.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            bet.setUser(currentUser.get());
        }
        betService.saveBet(bet);
        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(bet.getId()).toUri();
        return ResponseEntity.created(location).body(bet);
    }

}
    

