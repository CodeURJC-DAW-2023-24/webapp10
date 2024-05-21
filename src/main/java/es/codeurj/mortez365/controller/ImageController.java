package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.service.EventService;
import es.codeurj.mortez365.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Controller
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private UserService userSevice;
    
    @Autowired
    private EventService eventService;

    @PostMapping("/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor seleccione un archivo.");
            return "redirect:/profile";
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Optional<User> currentUser = userSevice.findByUsername(currentUserName);
            if (currentUser.isPresent()) {
                URI location = fromCurrentRequest().build().toUri();
                currentUser.get().setImageFile(location.toString());
                currentUser.get().setImage(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
                userSevice.save(currentUser.get());
            }
            // Success message
            redirectAttributes.addFlashAttribute("message", "La imagen de perfil se ha subido correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            // Error message
            redirectAttributes.addFlashAttribute("message", "Ocurrió un error al subir la imagen de perfil.");
        }

        return "redirect:/profile"; // Reload the page
    }

    @GetMapping("/uploadProfilePicture/")
    public ResponseEntity<Object> downloadImage() throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userSevice.findByUsername(currentUserName);
        if (currentUser.isPresent()) {
            if (currentUser.get().getImage() != null) {
                Resource file = new InputStreamResource(currentUser.get().getImage().getBinaryStream());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(currentUser.get().getImage().length())
                        .body(file);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/uploadEventPicture/{id}")
    public String uploadEventPicture(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
                                     @PathVariable Long id) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor seleccione un archivo.");
            return "redirect:/edit?id=" + id;
        }

        try {
            Optional<Event> e = eventService.findById(id);
            if (e.isPresent()) {
                URI location = fromCurrentRequest().build().toUri();
                e.get().setImageFile(location.toString());
                e.get().setImage(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
                eventService.save(e.get());
            }
            // Success message
            redirectAttributes.addFlashAttribute("message", "La imagen de perfil se ha subido correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            // Error message
            redirectAttributes.addFlashAttribute("message", "Ocurrió un error al subir la imagen de perfil.");
        }

        return "redirect:/edit?id=" + id; // Reload the page
    }

    // Image get for the betsadmin page
    @GetMapping("/uploadEventPicture/{id}")
    public ResponseEntity<Object> downloadEventImage(@PathVariable Long id) throws SQLException {
        Optional<Event> e = eventService.findById(id);
        if (e.isPresent()) {
            if (e.get().getImage() != null) {
                Resource file = new InputStreamResource(e.get().getImage().getBinaryStream());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(e.get().getImage().length())
                        .body(file);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
