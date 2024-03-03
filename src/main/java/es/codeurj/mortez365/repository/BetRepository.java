package es.codeurj.mortez365.repository;

import java.util.List;

import es.codeurj.mortez365.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;


import es.codeurj.mortez365.model.Bet;




public interface BetRepository extends JpaRepository<Bet, Long>{

    List<Bet> findByEvent(Event event);


}