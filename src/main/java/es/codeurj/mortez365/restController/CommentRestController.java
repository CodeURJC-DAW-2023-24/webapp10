package es.codeurj.mortez365.restController;

import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.CommentRepository;
import es.codeurj.mortez365.service.CommentService;
import es.codeurj.mortez365.service.EventService;
import es.codeurj.mortez365.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;


    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable long id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Collection<Comment>> getAllevents() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/event/{idE}")
    public ResponseEntity<Collection<Comment>> getCommentsByEvents(@PathVariable long idE) {
        Collection<Comment> comment = commentService.findByEvent(idE);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idE}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long idE) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<User> currentUser = userService.findByUsername(currentUserName);

        System.out.println("LLEGA");
        if(comment.getContent()==null){
            return ResponseEntity.badRequest().build();
        }
        if(eventService.findById(idE).isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            comment.setEvent(eventService.findById(idE).get());
            if(eventService.findById(comment.getEvent().getId()).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        if(currentUser.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            comment.setUser(currentUser.get());
        }
        commentService.save(comment);
        URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id) {
        Comment post = commentService.findById(id);
        if (post != null) {
            eventService.deleteComment(post.getEvent(), post);
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> replaceEvent(@RequestBody Comment newComment, @PathVariable Long id) {
       Comment comment = commentService.findById(id);

        if (comment!=null) {
            newComment.setId(id);
            newComment.setEvent(comment.getEvent());
            newComment.setUser(comment.getUser());
            commentService.save(newComment);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newComment.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
