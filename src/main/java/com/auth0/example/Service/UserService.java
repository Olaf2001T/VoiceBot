package com.auth0.example.Service;

import com.auth0.example.Entity.User;
import com.auth0.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByauthId(username);
    }


    public void updateLatestMessages(String authId, String sentMessage, String receivedMessage) {
        User user = userRepository.findById(authId)
                .orElse(new User(authId, "", ""));
        user.setLastSendMessege(sentMessage);
        user.setLastReceivedMessege(receivedMessage);
        userRepository.save(user);
    }

    public User getUserConversationHistory(String authId) {
        return userRepository.findById(authId)
                .orElse(new User(authId, "", ""));
    }

}
