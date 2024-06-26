package es.codeurj.mortez365.restController;

import es.codeurj.mortez365.DTO.RegistrationDataDTO;
import es.codeurj.mortez365.DTO.UserDataDTO;
import es.codeurj.mortez365.controller.AppController;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.service.UserService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@CrossOrigin( methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AppController.class);


    @Operation(summary = "Get All Users", description = "Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDataDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Iterable<UserDataDTO>> getUsers() {
        List<User> users =  userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<UserDataDTO> usersList = new ArrayList<>();
        for (User user : users) {
            UserDataDTO dto = new UserDataDTO(user.getId(), user.getName(), user.getFirstsurname(), user.getSecondsurname(), user.getEmail(), user.getUsername(), user.getAdress(), user.getPostcode(), user.getTelephone(), user.getDni(), user.getBirthdate(),user.getRoles());
            usersList.add(dto);
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @Operation(summary = "Get User by ID", description = "Retrieve a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDataDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDataDTO> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            UserDataDTO dto = new UserDataDTO(user.get().getId(), user.get().getName(), user.get().getFirstsurname(), user.get().getSecondsurname(), user.get().getEmail(), user.get().getUsername(), user.get().getAdress(), user.get().getPostcode(), user.get().getTelephone(), user.get().getDni(), user.get().getBirthdate(), user.get().getRoles());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete User", description = "Delete a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Create New User", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        try {/* 
            log.info("ESTE ES EL NOMBRE DEL USUARIO: ", user.getName());
            log.info("ESTE ES EL DINERO QUE TIENE LA WALLET: ", user.getWallet().getMoney());
             
            
            log.info("IMAGENES BIEN");*/
    
            User savedUser = userService.save(user);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).body(savedUser);
        } catch (Exception e) {
            log.info("EL POST TIENE PROBLEMAS");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Replace User", description = "Replace an existing user with a new user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully replaced", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) throws IOException, SQLException {
      Optional< User> user = userService.findById(id);
      if (user.isPresent()) {
          Blob blob = user.get().getImage();
          int blobLength = (int) blob.length();
          byte[] blobAsBytes = blob.getBytes(1, blobLength);
          blob.free();
          return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(blobAsBytes);
      } else {
          return ResponseEntity.noContent().build();
      }
    }


      @PostMapping("/image/{id}")
      public ResponseEntity<Object> uploadImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
              throws IOException {

                
          User user = userService.findById(id).orElseThrow();
  
          URI location = fromCurrentRequest().build().toUri();
  
          user.setImageFile(location.toString());
          user.setImage(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
          userService.save(user);
  
          return ResponseEntity.created(location).build();
      
      }
  
      @DeleteMapping("/image/{id}")
      public ResponseEntity<Object> deleteImage(@PathVariable long id)
          throws IOException {
          User user = userService.findById(id).orElseThrow();
          user.setImageFile(null);
          user.setImage(null);
          userService.save(user);
          return ResponseEntity.noContent().build();
  }


    @PutMapping("/image/{id}")
    public ResponseEntity<Object> updateImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
            throws IOException {

            User user = userService.findById(id).orElseThrow();

            URI location = fromCurrentRequest().build().toUri();

            user.setImageFile(location.toString());
            user.setImage(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            userService.save(user);

            return ResponseEntity.created(location).build();
        
    }

    @GetMapping("/me")
    public ResponseEntity<UserDataDTO> getUserMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userService.findByUsername(currentUserName);
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            UserDataDTO userDTO = new UserDataDTO(user.getId(), user.getName(), user.getFirstsurname(), user.getSecondsurname(), user.getEmail(), user.getUsername(), user.getAdress(), user.getPostcode(), user.getTelephone(), user.getDni(), user.getBirthdate(),user.getRoles());
            return ResponseEntity.ok().body(userDTO);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
  
}
