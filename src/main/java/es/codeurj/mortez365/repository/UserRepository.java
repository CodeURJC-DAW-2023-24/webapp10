package es.codeurj.mortez365.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurj.mortez365.model.User;
import org.springframework.stereotype.Repository;


//The UserRepository interface is used to save the user in the database.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByUsername(String username);
}
