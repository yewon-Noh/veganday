package inhatc.insecure.veganday.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import inhatc.insecure.veganday.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("select userId from User where userId = ?1")
	Optional<User> findById(String userId);
	
}