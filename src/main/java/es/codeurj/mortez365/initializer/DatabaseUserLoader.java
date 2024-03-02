package es.codeurj.mortez365.initializer;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Component
public class DatabaseUserLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDatabase() {

        userRepository.save(new User("Alaro", "Pindado", "Castiñeira",
                "emailfalso@gmail.com", Date.from(Instant.now()),"Español","123456789V",
                "user", passwordEncoder.encode("pass"),false,"Tu mama",
                "12345","123456789", List.of("USER")));

    }
}
