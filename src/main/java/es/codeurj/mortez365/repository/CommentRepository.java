package es.codeurj.mortez365.repository;

import es.codeurj.mortez365.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteById(long id);
}
