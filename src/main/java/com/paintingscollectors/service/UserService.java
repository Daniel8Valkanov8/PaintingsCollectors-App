package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }


    public boolean register(UserRegisterDTO data) {
        Optional<User> existingUser = userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail());
        if (existingUser.isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        this.userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDTO data) {
        Optional<User> byUsername = userRepository.findByUsername(data.getUsername());
        if (byUsername.isEmpty()) {
            return false;
        }
        boolean passMatch = passwordEncoder
            .matches(data.getPassword(), byUsername.get().getPassword());
        if (!passMatch) {
            return false;
        }
        userSession.login(byUsername.get().getId(), data.getUsername());
        return true;
    }

    @Transactional
    public Set<Painting> findFavourites(Long id) {
        return userRepository.findById(id)
                .map(User::getFavoritePaintings)
                .orElseGet(HashSet::new);

    }
}
