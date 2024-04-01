package es.codeurj.mortez365.controller;



import java.sql.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import es.codeurj.mortez365.model.Wallet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.UserSevice;
import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurj.mortez365.DTO.*;





@Controller
@RequestMapping("/register")
public class UserController {


    private UserSevice userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserSevice userService, PasswordEncoder passwordEncoder) {
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
    @RequestParam String username, @RequestParam String password, @RequestParam byte[] image,
    @RequestParam String card_number, @RequestParam String cvv, @RequestParam String expired_date) {

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
    user.setPassword(passwordEncoder.encode(password));
    user.setImage(image);

    String real_date = expired_date.substring(5) + "/" + expired_date.substring(2,4);
    Wallet w = new Wallet(card_number, cvv, real_date, user);
    user.setWallet(w);
   



    userService.save(user);
    System.out.println("User Saved");
    return "redirect:/index";
}
 //The initAdmin method is used to initialize the admin user in the database.
    @PostConstruct
    public void initAdmin() {
        List<String> roles = Arrays.asList("USER", "ADMIN");
        byte[] false_image = new byte[8];
        User u = new User("admin", "Admin", "PorDefecto", "admin@gmail.com", new java.util.Date(), "Sierra Leona", "459087S", "admin", passwordEncoder.encode("adminpass"), true, "Calle", "2313", "1232131", roles, false_image);
        Wallet wallet = new Wallet("123214", "980", "06/27", u);
        u.setWallet(wallet);
        userService.save(u);
    }
 }
   
    
    
    

