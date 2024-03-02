package es.codeurj.mortez365.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventRepository events;

    

    @PostConstruct
    public void init() {
     
        events.save(new Event("Atlético de Madrid - Real Madrid", "assets/img/laliga/madridatleti.webp", "LaLiga","Fútbol"));
        events.save(new Event("Villarreal - Tenerife", "assets/img/laliga/sevillatenerife.webp", "LaLiga","Fútbol"));
        events.save(new Event("Levante - Leganés", "assets/img/laliga/levanteleganes.webp", "LaLiga","Fútbol"));
        events.save(new Event("Real Madrid - Girona", "assets/img/laliga/madridgirona.webp", "LaLiga","Fútbol"));
        events.save(new Event("Cádiz - Betis", "assets/img/laliga/cadizbetis.webp", "LaLiga","Fútbol"));
        events.save(new Event("Getafe - Celta", "assets/img/laliga/getafecelta.webp", "LaLiga","Fútbol"));
        events.save(new Event("Manchester United - Fulham", "assets/img/premierleague/manchesterunitedfulham.webp", "PremierLeague","Fútbol"));
        events.save(new Event("Arsenal - New Castle United", "assets/img/premierleague/arsenalnewcastleunited.webp", "PremierLeague","Fútbol"));  
        events.save(new Event("Brighton - Everton", "assets/img/premierleague/brightoneverton.webp", "PremierLeague","Fútbol"));   
        events.save(new Event("Crystal Palace - Burnley", "assets/img/premierleague/crystalpalaceburnley.webp", "PremierLeague","Fútbol"));       
        events.save(new Event("West Ham - Brentford", "assets/img/premierleague/westhambrentford.webp", "PremierLeague","Fútbol"));   
        events.save(new Event("Bourner Mouth - Manchester City", "assets/img/premierleague/bournermouthmanchestercity.webp", "PremierLeague","Fútbol"));   
        events.save(new Event("Coventry City - Maidstone United", "assets/img/facup/coventrycitymaidstoneunited.webp", "FACup","Fútbol"));  
        events.save(new Event("Bournemouth - Leicester City", "assets/img/facup/bournemouthleicestercity.webp", "FACup","Fútbol"));
        events.save(new Event("Blackburn Rovers - Newcastle United", "assets/img/facup/blackburnroversnewcastleunited.webp", "FACup","Fútbol"));
    

    
    }

    @GetMapping("/events")
    public List<Event> getAllevents() {
        return events.findAll();
    }
   
    

    
}


 
    

