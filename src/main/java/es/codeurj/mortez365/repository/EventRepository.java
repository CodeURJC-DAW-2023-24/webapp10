package es.codeurj.mortez365.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import es.codeurj.mortez365.model.Event;




public interface EventRepository extends JpaRepository<Event, Long>{
    

    
}