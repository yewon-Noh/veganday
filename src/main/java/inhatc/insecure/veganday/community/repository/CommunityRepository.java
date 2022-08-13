package inhatc.insecure.veganday.community.repository;

import inhatc.insecure.veganday.community.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
