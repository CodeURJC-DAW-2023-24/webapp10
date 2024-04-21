package es.codeurj.mortez365.restController;

import java.util.Collection;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.service.BetService;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/bets")
public class BetRestController {

    @Autowired
    private BetService betService;


    @GetMapping("/")
    public ResponseEntity<Collection<Bet>> getBets() {
        Collection<Bet> bets = betService.findAllBets();
        return new ResponseEntity<Collection<Bet>>(bets, HttpStatus.OK);

    }
    
}
    

