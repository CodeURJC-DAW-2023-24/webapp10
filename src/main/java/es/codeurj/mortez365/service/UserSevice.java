package es.codeurj.mortez365.service;




import org.springframework.stereotype.Service;


import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;

@Service
public class UserSevice {
    private UserRepository userRepository;

    public UserSevice(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    
    
    public User save(User user) {
        
      
        return userRepository.save(user);
    }
}
