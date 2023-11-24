package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.user.domain.User;
import com.ara.todayoutfit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }


}
