package inhatc.insecure.veganday.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.insecure.veganday.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findById(String user_id);
	
}