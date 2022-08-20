package inhatc.insecure.veganday.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import inhatc.insecure.veganday.user.model.User;
import inhatc.insecure.veganday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    public User create(String userId, String userName, String userEmail) {
    	User user = new User();
    	user.setUserId(userId);
    	user.setUserEmail(userEmail);
    	user.setUserName(userName);
    	userRepository.save(user);
        return user;
    }
    
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }
}