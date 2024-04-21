package es.codeurj.mortez365.restController;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



@RestController
@RequestMapping("/api/events")
public class EventRestController {
  

    @Autowired
    private EventService eventService;


    @Operation(summary = "Get All Events", description = "Retrieve all events paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Page<Event>> getAllevents(Pageable pageable) {
        Page<Event> page = eventService.findAll(pageable);
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
        Optional<Event> event = eventService.findById(id);
        
        if (event.isPresent()) {
           return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Event", description = "Delete an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Replace Event by ID", description = "Replace an event by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get Events by Championship", description = "Retrieve events by championship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "No events found for the specified championship"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/championship/{championship}")
    public Collection <Event> getEventByChampionship(@PathVariable String championship){
        return eventService.findByChampionship(championship);
    
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
        return eventService.findBySport(sport);
    }

    @Operation(summary = "Get Image by Event ID", description = "Retrieve image URL by event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image URL successfully retrieved"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    
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

    @PostMapping("/image/{id}")
	public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		Event event = eventService.findById(id).orElseThrow();

		URI location = fromCurrentRequest().build().toUri();

		event.setImageFile(location.toString());
		event.setImage(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
		eventService.save(event);

		return ResponseEntity.created(location).build();
	}

    @DeleteMapping("/image/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable long id)
        throws IOException {
        Event event = eventService.findById(id).orElseThrow();
        event.setImageFile(null);
        event.setImage(null);
        eventService.save(event);
        return ResponseEntity.noContent().build();
}


}


 
    

