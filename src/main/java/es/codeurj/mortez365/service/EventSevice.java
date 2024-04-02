package es.codeurj.mortez365.service;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import es.codeurj.mortez365.model.Event;

import es.codeurj.mortez365.repository.EventRepository;
import jakarta.annotation.PostConstruct;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import javax.sql.rowset.serial.SerialException;


@Service
public class EventSevice {
   
    private EventRepository events;
//The EventSevice class is used to save the event in the database.
    public EventSevice(EventRepository events) {
        super();
        this.events = events;
    }

     @PostConstruct
    public void init() throws SerialException, SQLException {

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
    
       for (int i = 0; i < events.count(); i++) {
            Event event = events.findAll().get(i);
            byte[] bytes = event.getImageFile().getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            event.setImage(blob);
            events.save(event);
        }
    
    }
    public Event save(Event event) {
      
        return events.save(event);
    }
  
    public Page<Event> findAll(Pageable pageable) {
        return events.findAll(pageable);
    }
    public Optional<Event> findById(Long id) {
        return events.findById(id);
    }
    
    public List<Event> findByChampionship (String championship){
        return events.findByChampionship(championship);
    }

    public List<Event> findBySport (String sport){
        return events.findBySport(sport);
    }

public void deleteById(Long id) {
        events.deleteById(id);
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
