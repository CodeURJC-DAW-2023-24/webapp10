package es.codeurj.mortez365.initializer;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("usuario_prueba");
        user.setPassword("contraseña123");
        user.setEmail("usuario@example.com");
        userRepository.save(user);
    }
}
