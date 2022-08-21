package com.ara.todayoutfit.user.service;

import com.ara.todayoutfit.user.model.Member;

import java.util.Optional;

public interface MemberService {

    Member save(Member user);

    void delete(Member user);

    Optional<Member> findById(String id);

}
