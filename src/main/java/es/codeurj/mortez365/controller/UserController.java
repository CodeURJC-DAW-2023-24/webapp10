package es.codeurj.mortez365.controller;



import java.io.IOException;
import java.sql.Date;

import java.util.Arrays;
import java.util.List;


import es.codeurj.mortez365.model.Wallet;


import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.UserService;
import jakarta.annotation.PostConstruct;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/register")
public class UserController {


    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        super();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

  
    @GetMapping
    public String registerForm() {

        return "register";
    }
 
  //The registerUser method is used to register the user in the database.
    @PostMapping
    public String registerUser(@RequestParam String name,
    @RequestParam String firstsurname, @RequestParam String secondsurname, @RequestParam Date birthdate,
    @RequestParam String nationality, @RequestParam String dni, @RequestParam String adress,
    @RequestParam String postcode, @RequestParam String telephone, @RequestParam String email,
    @RequestParam String username, @RequestParam String password, @RequestParam MultipartFile imageFile,
    @RequestParam String card_number, @RequestParam String cvv, @RequestParam String expired_date) throws IOException {

    User user = new User();
    user.setName(name);
    user.setFirstsurname(firstsurname);
    user.setSecondsurname(secondsurname);
    user.setBirthdate(birthdate);
    user.setNationality(nationality);
    user.setDni(dni);
    user.setAdress(adress);
    user.setPostcode(postcode);
    user.setTelephone(telephone);
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(password);
    user.setImageFile(imageFile.getOriginalFilename());

    Resource image = new ClassPathResource(user.getImageFile());
    user.setImage(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
  

    String real_date = expired_date.substring(5) + "/" + expired_date.substring(2,4);
    Wallet w = new Wallet(card_number, cvv, real_date, user);
    user.setWallet(w);


    userService.save(user);
    System.out.println("User Saved");
    return "redirect:/index";
}
 //The initAdmin method is used to initialize the admin user in the database.
    @PostConstruct
    public void initAdmin() throws IOException {
        // Create demo administrator
        List<String> roles = Arrays.asList("USER", "ADMIN");
        User u = new User("admin", "Admin", "PorDefecto", "admin@gmail.com", new java.util.Date(), "Sierra Leona", "459087S", "admin", "adminpass", true, "Calle", "2313", "1232131", roles,"assets/img/laliga/leyenda.jpeg");
        Resource image = new ClassPathResource(u.getImageFile());
        u.setImage(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        Wallet wallet = new Wallet("123214", "980", "06/27", u);
        u.setWallet(wallet);
        userService.save(u);

        // Create demo user
        List<String> rolesU = Arrays.asList("USER");
        User u2 = new User("user", "Usuario", "PorDefecto", "user@gmail.com", new java.util.Date(), "República Democrática Argelina", "23234560S", "user", "userpass", false, "Calle", "28914", "678453664", rolesU,"assets/img/laliga/internalpointer.png");
        Resource image2 = new ClassPathResource(u2.getImageFile());
        u2.setImage(BlobProxy.generateProxy(image2.getInputStream(), image2.contentLength()));
        Wallet wallet2 = new Wallet("45642354", "777", "02/30", u2);
        u2.setWallet(wallet2);
        userService.save(u2);
    }
 }
   
    
    
    

