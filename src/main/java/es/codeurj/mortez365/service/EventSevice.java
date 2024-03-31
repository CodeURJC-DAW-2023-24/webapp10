package es.codeurj.mortez365.service;




import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import es.codeurj.mortez365.model.Event;

import es.codeurj.mortez365.repository.EventRepository;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class EventSevice {
   
    private EventRepository events;
//The EventSevice class is used to save the event in the database.
    public EventSevice(EventRepository events) {
        super();
        this.events = events;
    }

     @PostConstruct
    public void init() {

         var v = new Date();
         v.setMinutes(v.getMinutes() + 1);
        events.save(new Event("Villarreal - Tenerife", "assets/img/laliga/sevillatenerife.webp", "LaLiga","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Levante - Leganés", "assets/img/laliga/levanteleganes.webp", "LaLiga","Fútbol", v));
        events.save(new Event("Real Madrid - Girona", "assets/img/laliga/madridgirona.webp", "LaLiga","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Cádiz - Betis", "assets/img/laliga/cadizbetis.webp", "LaLiga","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Getafe - Celta", "assets/img/laliga/getafecelta.webp", "LaLiga","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Manchester United - Fulham", "assets/img/premierleague/manchesterunitedfulham.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Arsenal - New Castle United", "assets/img/premierleague/arsenalnewcastleunited.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Brighton - Everton", "assets/img/premierleague/brightoneverton.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Crystal Palace - Burnley", "assets/img/premierleague/crystalpalaceburnley.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("West Ham - Brentford", "assets/img/premierleague/westhambrentford.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Bourner Mouth - Manchester City", "assets/img/premierleague/bournermouthmanchestercity.webp", "PremierLeague","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Coventry City - Maidstone United", "assets/img/facup/coventrycitymaidstoneunited.webp", "FACup","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Bournemouth - Leicester City", "assets/img/facup/bournemouthleicestercity.webp", "FACup","Fútbol", new Date(124, 3, 15, 15, 30)));
        events.save(new Event("Blackburn Rovers - Newcastle United", "assets/img/facup/blackburnroversnewcastleunited.webp", "FACup","Fútbol", new Date(124, 3, 15, 15, 30)));
    

    
    }
    public Event save(Event event) {
      
        return events.save(event);
    }

    public List<Event> filterFinalizedEvents(List<Event> events) {
        List<Event> finalizedEvents = new ArrayList<>();
        for (Event event : events) {
            if (!event.getFinished()) {
                finalizedEvents.add(event);
            }
        }
        return finalizedEvents;
    }
}
