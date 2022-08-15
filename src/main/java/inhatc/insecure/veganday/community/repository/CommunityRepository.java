package inhatc.insecure.veganday.community.repository;

import inhatc.insecure.veganday.community.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.title like %?1%")
    List<Board> findList(String title, Sort sort);

    @Query("select new map ( b.bid as bid, b.title as title, b.userId as userId, b.hit as hit, b.writeDt as writeDt ) from Board b where b.title like %?1%")
    Page<Board> findListNonCnWithPage(String title, Pageable pageable);

}
