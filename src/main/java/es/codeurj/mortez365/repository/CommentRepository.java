package es.codeurj.mortez365.repository;

import es.codeurj.mortez365.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM comment WHERE event = ?1", nativeQuery = true)
    List<Comment> getCommentByEvent(long id);

    void deleteById(long id);
}
