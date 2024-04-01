package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.DTO.UserDataDTO;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.EventSevice;
import es.codeurj.mortez365.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserSevice userService;


    @GetMapping("/")
    public ResponseEntity<Iterable<UserDataDTO>> getUsers() {
        List<User> users =  userService.findAll();
        Collection<UserDataDTO> usersList = new ArrayList<>();
        for (User user : users) {
            UserDataDTO dto = new UserDataDTO(user.getName(), user.getFirstsurname(), user.getSecondsurname(), user.getEmail(), user.getUsername());
            usersList.add(dto);
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
