package es.codeurj.mortez365.service;



import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.repository.CommentRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import es.codeurj.mortez365.model.Event;

import es.codeurj.mortez365.repository.EventRepository;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;


@Service
public class EventService {

    @Autowired
    private EventRepository events;

    @Autowired
    private CommentRepository commentRepository;
//The EventSevice class is used to save the event in the database.
    public EventService(EventRepository events) {
        super();
        this.events = events;
    }

     @PostConstruct
    public void init() throws SerialException, SQLException, IOException {

         var v = new Date();
         v.setMinutes(v.getMinutes() + 1);
         
        events.save(new Event("Villarreal - Tenerife", "assets/img/laliga/carletes.jpeg", "LaLiga","Fútbol", new Date(124, 4, 15, 15, 30)));

        events.save(new Event("Levante - Leganés", "assets/img/laliga/carletes.jpeg", "LaLiga","Fútbol", v));
        events.save(new Event("Real Madrid - Girona", "assets/img/laliga/madridgirona.jpg", "LaLiga","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Cádiz - Betis", "assets/img/laliga/cadizbetis.jpg", "LaLiga","Fútbol", new Date(124, 4, 15, 15, 30)));

        events.save(new Event("Getafe - Celta", "assets/img/laliga/getafecelta.jpg", "LaLiga","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Manchester United - Fulham", "assets/img/premierleague/manchesterunitedfulham.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Arsenal - New Castle United", "assets/img/premierleague/arsenalnewcastleunited.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Brighton - Everton", "assets/img/premierleague/brightoneverton.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Crystal Palace - Burnley", "assets/img/premierleague/crystalpalaceburnley.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("West Ham - Brentford", "assets/img/premierleague/westhambrentford.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Bourner Mouth - Manchester City", "assets/img/premierleague/bournermouthmanchestercity.jpg", "PremierLeague","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Coventry City - Maidstone United", "assets/img/facup/coventrycitymaidstoneunited.jpg", "FACup","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Bournemouth - Leicester City", "assets/img/facup/bournemouthleicestercity.jpg", "FACup","Fútbol", new Date(124, 4, 15, 15, 30)));
        events.save(new Event("Blackburn Rovers - Newcastle United", "assets/img/facup/blackburnroversnewcastleunited.jpg", "FACup","Fútbol", new Date(124, 4, 15, 15, 30)));
    
       for(Event event: events.findAll()) {
            Resource image = new ClassPathResource(event.getImageFile());
            event.setImage(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
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

    public void deleteComment(Event checkEvent, Comment comment) {
        checkEvent.deleteComment(comment);
        commentRepository.deleteById(comment.getId());
    }
}
