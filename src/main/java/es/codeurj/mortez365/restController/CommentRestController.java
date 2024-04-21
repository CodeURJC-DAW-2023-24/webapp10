package es.codeurj.mortez365.restController;

import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.repository.CommentRepository;
import es.codeurj.mortez365.service.CommentService;
import es.codeurj.mortez365.service.EventSevice;
import es.codeurj.mortez365.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserSevice userService;

    @Autowired
    private EventSevice eventService;


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

    @PostMapping("/{idE}/{idU}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long idE, @PathVariable Long idU) {

        if(comment.getContent()==null){
            return ResponseEntity.badRequest().build();
        }
        if(eventService.findById(idE).isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            comment.setEvent(eventService.findById(idE).get());
            comment.setEventName(eventService.findById(idE).get().getName());
            if(eventService.findById(comment.getEvent().getId()).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        if(userService.findById(idU).isEmpty()){
            return ResponseEntity.badRequest().build();
        } else{
            comment.setUserName(userService.findById(idU).get().getName());
            comment.setUser(userService.findById(idU).get());
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
            commentService.deleteById(id);
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
            newComment.setEventName(comment.getEventName());
            newComment.setUser(comment.getUser());
            newComment.setUserName(comment.getUserName());
            commentService.save(newComment);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newComment.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
