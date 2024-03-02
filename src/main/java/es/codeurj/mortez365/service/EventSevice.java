package es.codeurj.mortez365.service;


import java.util.Arrays;

import org.springframework.stereotype.Service;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.Role;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.EventRepository;
import es.codeurj.mortez365.repository.UserRepository;

@Service
public class EventSevice {
    private EventRepository eventRepository;

    public EventSevice(EventRepository eventRepository) {
        super();
        this.eventRepository = eventRepository;
    }
    
    
    public Event save(Event event) {
      
        return eventRepository.save(event);
    }
}
