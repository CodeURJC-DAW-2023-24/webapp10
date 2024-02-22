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
     
        events.save(new Event("Atlético de Madrid - Real Madrid", "assets/img/partidos/madridatleti.webp", "La Liga"));
        events.save(new Event("Villarreal - Tenerife", "assets/img/partidos/sevillatenerife.webp", "La Liga"));
        events.save(new Event("Levante - Leganés", "assets/img/partidos/levanteleganes.webp", "La Liga"));
        events.save(new Event("Real Madrid - Girona", "assets/img/partidos/madridgirona.webp", "La Liga"));
        events.save(new Event("Cádiz - Betis", "assets/img/partidos/cadizbetis.webp", "La Liga"));
        events.save(new Event("Getafe - Celta", "assets/img/partidos/getafecelta.webp", "La Liga"));
            
    }

    @GetMapping("/events")
    public List<Event> getAllevents() {
        return events.findAll();
    }

    

    
}


 
    

