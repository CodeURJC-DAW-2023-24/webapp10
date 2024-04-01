package es.codeurj.mortez365.service;




import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.EventRepository;
import org.springframework.stereotype.Service;


import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSevice {
    private UserRepository userRepository;
//The UserSevice class is used to save the user in the database.
    public UserSevice(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    
    
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

