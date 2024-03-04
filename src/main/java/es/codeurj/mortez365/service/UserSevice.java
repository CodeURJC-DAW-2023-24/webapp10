package es.codeurj.mortez365.service;




import org.springframework.stereotype.Service;


import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;

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
}
