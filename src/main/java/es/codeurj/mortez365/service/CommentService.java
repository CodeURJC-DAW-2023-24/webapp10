package es.codeurj.mortez365.service;


import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.repository.CommentRepository;
import es.codeurj.mortez365.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private EventSevice events;

    @Autowired
    private CommentRepository commentRepository;

    public void save(Comment comment) {
        comment.setUser(comment.getUser());
        commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public Collection<Comment> findByEvent(Long id) {
        Collection<Comment> commentsByEvent = new ArrayList<>();
        if(events.findById(id).isPresent()){
            return commentRepository.getCommentByEvent(id);
        }
        return commentsByEvent;
    }

    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }
}
