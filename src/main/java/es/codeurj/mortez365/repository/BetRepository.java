package es.codeurj.mortez365.repository;

import java.util.List;
import java.util.Optional;

import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import es.codeurj.mortez365.model.Bet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


//The BetRepository interface is used to save the bet in the database.
public interface BetRepository extends JpaRepository<Bet, Long>{

    List<Bet> findByEvent(Event event);

    List<Bet> findByUser(User user);

    Page<Bet> findByUser(Optional<User> user, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM bet WHERE user = ?1", nativeQuery = true)
    List<Bet> getBetByUser(long id);


}