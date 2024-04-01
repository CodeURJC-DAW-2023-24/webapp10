package es.codeurj.mortez365.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import es.codeurj.mortez365.DTO.UserDataDTO;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.service.EventSevice;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/events")
public class EventRestController {
  

    @Autowired
    private EventSevice eventService;

     
    
@GetMapping("/")
public ResponseEntity<Page<Event>> getAllevents(Pageable pageable) {
    Page<Event> page = eventService.findAll(pageable);
    return new ResponseEntity<>(page, HttpStatus.OK);
}

    @PostMapping("/")
    public ResponseEntity<Event> newEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventService.save(newEvent);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedEvent.getId()).toUri();
        return ResponseEntity.created(location).body(savedEvent);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.findById(id);
        
        if (event.isPresent()) {
           return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
       Event event = eventService.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> replaceEvent(@RequestBody Event newEvent, @PathVariable Long id) {
        Optional<Event> event = eventService.findById(id);
        
        if (event.isPresent()) {
            newEvent.setId(id);
            eventService.save(newEvent);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/championship/{championship}")
    public Collection <Event> getEventByChampionship(@PathVariable String championship){
        return eventService.findByChampionship(championship);
    
    }
    @GetMapping("/sport/{sport}")
    public Collection <Event> getEventBySport(@PathVariable String sport){
        return eventService.findBySport(sport);
    }
    
  @GetMapping("/image/{id}")
  public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws IOException, SQLException {
    Optional< Event> event = eventService.findById(id);
    if (event.isPresent()) {
        Blob blob = event.get().getImage();
        int blobLength = (int) blob.length();  
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        blob.free();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(blobAsBytes);
    } else {
        return ResponseEntity.noContent().build();
    }
    }

} 


 
    

