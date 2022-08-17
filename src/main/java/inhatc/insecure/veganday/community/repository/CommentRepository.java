package inhatc.insecure.veganday.community.repository;

import inhatc.insecure.veganday.community.model.Board;
import inhatc.insecure.veganday.community.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.bid = ?1")
    List<Comment> findComments(Long bid);
}