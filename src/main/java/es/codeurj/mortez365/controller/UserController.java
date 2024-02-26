package es.codeurj.mortez365.controller;



import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.UserSevice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





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
    @RequestParam String password) {
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
    user.setPassword(password);


    userService.save(user);
    System.out.println("User Saved");
    return "redirect:/login";
}
 

   
    
    
    
}
