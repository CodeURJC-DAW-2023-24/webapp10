package es.codeurj.mortez365.controller;



import java.sql.Date;

import java.util.Arrays;
import java.util.List;


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
    @RequestParam String firstsurname,
    @RequestParam String secondsurname,
    @RequestParam Date birthdate,
    @RequestParam String nationality,
    @RequestParam String dni,
    @RequestParam String adress,
    @RequestParam String postcode,
    @RequestParam String telphone,
    @RequestParam String email,
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam Double money) {
    User user = new User();
    user.setName(name);
    user.setFirstsurname(firstsurname);
    user.setSecondsurname(secondsurname);
    user.setBirthdate(birthdate);
    user.setNationality(nationality);
    user.setDni(dni);
    user.setAdress(adress);
    user.setPostcode(postcode);
    user.setTelphone(telphone);
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setMoney(money);
   



    userService.save(user);
    System.out.println("User Saved");
    return "redirect:/index";
}
 //The initAdmin method is used to initialize the admin user in the database.
    @PostConstruct
    public void initAdmin() {
        List<String> roles = Arrays.asList("USER", "ADMIN");
        userService.save(new User("admin", passwordEncoder.encode("adminpass"), roles));
    }

 }
   
    
    
    

