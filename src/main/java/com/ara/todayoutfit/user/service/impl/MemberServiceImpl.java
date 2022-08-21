package com.ara.todayoutfit.user.service.impl;

import com.ara.todayoutfit.user.model.Member;
import com.ara.todayoutfit.user.repository.MemberRepository;
import com.ara.todayoutfit.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository userRepository;

    @Override
    public Member save(Member user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Member user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<Member> findById(String id) {
        return userRepository.findById(id);
    }


}
