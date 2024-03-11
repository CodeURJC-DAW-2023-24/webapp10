package es.codeurj.mortez365.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import es.codeurj.mortez365.model.Event;



//The EventRepository interface is used to save the event in the database.
public interface EventRepository extends JpaRepository<Event, Long>{

        List<Event> findByChampionship(String championship);
    
}