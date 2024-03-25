package es.codeurj.mortez365.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



import org.springframework.http.ResponseEntity;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.service.EventSevice;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventRepository events;

    @Autowired
    private EventSevice eventService;

     
   

    @GetMapping("/")
    public Collection<Event> getAllevents() {
        return events.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Event> newEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventService.save(newEvent);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedEvent.getId()).toUri();
        return ResponseEntity.created(location).body(savedEvent);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> getEventById(@PathVariable Long id) {
        Optional<Event> event = events.findById(id);
        
        if (event.isPresent()) {
           return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
       Event event = events.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            events.deleteById(id);
            return ResponseEntity.ok(event);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> replaceEvent(@RequestBody Event newEvent, @PathVariable Long id) {
        Optional<Event> event = events.findById(id);
        if (event.isPresent()) {
            newEvent.setId(id);
            events.save(newEvent);
            return ResponseEntity.ok(newEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}


 
    

