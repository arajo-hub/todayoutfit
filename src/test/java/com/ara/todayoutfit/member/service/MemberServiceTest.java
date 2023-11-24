package com.ara.todayoutfit.member.service;

import com.ara.todayoutfit.user.domain.User;
import com.ara.todayoutfit.user.repository.UserRepository;
import com.ara.todayoutfit.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void save() {
        User user = User.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userService.save(user);
        Optional<User> userById = userRepository.findById(user.getId());
        User result = userById.isPresent() ? userById.get() : null;
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPw(), result.getPw());
    }

    @Test
    void delete() {
        User user = User.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userRepository.save(user);
        userService.deleteById(user.getId());
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void findById() {
        User user = User.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userService.save(user);
        Optional<User> userById = userRepository.findById(user.getId());
        User result = userById.isPresent() ? userById.get() : null;

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPw(), result.getPw());
    }
}