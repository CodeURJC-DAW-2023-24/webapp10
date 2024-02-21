package es.codeurj.mortez365.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import es.codeurj.mortez365.model.Match;
import es.codeurj.mortez365.repository.MatchRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/matches")
public class MatchController {
    @Autowired
    private MatchRepository matches;

    

    @PostConstruct
    public void init() {
        matches.save(new Match("Atlético de Madrid - Real Madrid", "assets/img/partidos/madridatleti.webp", "La Liga"));
        matches.save(new Match("Villarreal - Tenerife", "assets/img/partidos/sevillatenerife.webp", "La Liga"));
        matches.save(new Match("Levante - Leganés", "assets/img/partidos/levanteleganes.webp", "La Liga"));
        matches.save(new Match("Real Madrid - Girona", "assets/img/partidos/madridgirona.webp", "La Liga"));
        matches.save(new Match("Cádiz - Betis", "assets/img/partidos/cadizbetis.webp", "La Liga"));
        matches.save(new Match("Getafe - Celta", "assets/img/partidos/getafecelta.webp", "La Liga"));
            
    }

    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matches.findAll();
    }

    
}


 
    

