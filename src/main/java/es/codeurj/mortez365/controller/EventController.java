package es.codeurj.mortez365.controller;

import java.net.URI;
import java.util.Optional;
import java.util.Collection;

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
import org.springframework.http.ResponseEntity;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.service.EventSevice;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventRepository events;

    @Autowired
    private EventSevice eventService;

    @Operation(summary = "Get All Events", description = "Retrieve all events paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Page<Event>> getAllevents(Pageable pageable) {
        Page<Event> page = events.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Operation(summary = "Create New Event", description = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<Event> newEvent(@RequestBody Event newEvent) {
        Event savedEvent = eventService.save(newEvent);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedEvent.getId()).toUri();
        return ResponseEntity.created(location).body(savedEvent);
    }

    @Operation(summary = "Get Event by ID", description = "Retrieve an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Replace Event by ID", description = "Replace an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Event> replaceEvent(@RequestBody Event newEvent, @PathVariable Long id) {
        Optional<Event> event = events.findById(id);
        
        if (event.isPresent()) {
            newEvent.setId(id);
            events.save(newEvent);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get Events by Championship", description = "Retrieve events by championship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "No events found for the specified championship"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/championship/{championship}")
    public Collection <Event> getEventByChampionship(@PathVariable String championship){
        return events.findByChampionship(championship);
    
    }

    @Operation(summary = "Get Events by Sport", description = "Retrieve events by sport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "No events found for the specified sport"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/sport/{sport}")
    public Collection <Event> getEventBySport(@PathVariable String sport){
        return events.findBySport(sport);
    }


    @Operation(summary = "Get Image by Event ID", description = "Retrieve image URL by event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image URL successfully retrieved"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/image/{id}")
    public ResponseEntity<String> getImageById(@PathVariable Long id){
        Optional<Event> event = events.findById(id);
        return event.map(value -> ResponseEntity.ok(value.getImage())).orElseGet(() -> ResponseEntity.noContent().build());
    }

    
} 


 
    

