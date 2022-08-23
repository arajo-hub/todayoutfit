package com.ara.todayoutfit.member.service;

import com.ara.todayoutfit.member.model.Member;
import com.ara.todayoutfit.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService userService;

    @Autowired
    private MemberRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void save() {
        Member user = Member.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userService.save(user);
        Optional<Member> userById = userRepository.findById(user.getId());
        Member result = userById.isPresent() ? userById.get() : null;
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPw(), result.getPw());
    }

    @Test
    void delete() {
        Member user = Member.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userRepository.save(user);
        userService.deleteById(user.getId());
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void findById() {
        Member user = Member.builder()
                .id("savetest")
                .pw("pw1234")
                .build();
        userService.save(user);
        Optional<Member> userById = userRepository.findById(user.getId());
        Member result = userById.isPresent() ? userById.get() : null;

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getPw(), result.getPw());
    }
}