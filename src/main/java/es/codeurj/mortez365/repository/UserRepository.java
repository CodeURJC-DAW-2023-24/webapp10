package es.codeurj.mortez365.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurj.mortez365.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}