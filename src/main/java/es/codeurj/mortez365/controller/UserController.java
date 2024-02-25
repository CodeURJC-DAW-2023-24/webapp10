package es.codeurj.mortez365.controller;

import java.sql.Date;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.UserSevice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;





@Controller

@RequestMapping("/register")

public class UserController {
    
  
    private UserSevice userService;

    public UserController(UserSevice userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @GetMapping
    public String registerForm() {
       
        return "register";
    }


    
    @PostMapping
    public String registerUser(@RequestParam String name, 
                           @RequestParam String firstSurname, 
                           @RequestParam String secondSurname, 
                           @RequestParam Date birthDate, 
                           @RequestParam String nationality, 
                           @RequestParam String dni, 
                           @RequestParam String address, 
                           @RequestParam String postalCode, 
                           @RequestParam String phone, 
                           @RequestParam String email, 
                           @RequestParam String username, 
                           @RequestParam String password
                           ) {
    User user = new User();
    user.setName(name);
    user.setFirstsurname(firstSurname);
    user.setSecondsurname(secondSurname);
    user.setBirthdate(birthDate);
    user.setNationality(nationality);
    user.setDni(dni);
    user.setAdress(address);
    user.setPostcode(postalCode);
    user.setTelphone(phone);
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(password);


    userService.save(user);
    return "redirect:/login";
}

    
    
    
}
