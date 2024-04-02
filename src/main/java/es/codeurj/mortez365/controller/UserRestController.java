package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.DTO.UserDataDTO;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserSevice userService;


    @GetMapping("/")
    public ResponseEntity<Iterable<UserDataDTO>> getUsers() {
        List<User> users =  userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<UserDataDTO> usersList = new ArrayList<>();
        for (User user : users) {
            UserDataDTO dto = new UserDataDTO(user.getName(), user.getFirstsurname(), user.getSecondsurname(), user.getEmail(), user.getUsername());
            usersList.add(dto);
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDTO> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            UserDataDTO dto = new UserDataDTO(user.get().getName(), user.get().getFirstsurname(), user.get().getSecondsurname(), user.get().getEmail(), user.get().getUsername());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> newEvent(@RequestBody User newUser) {
        User savedUser = userService.save(newUser);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> replaceEvent(@RequestBody User newUser, @PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            newUser.setId(id);
            userService.save(newUser);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
