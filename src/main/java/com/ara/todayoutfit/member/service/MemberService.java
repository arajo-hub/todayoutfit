package com.ara.todayoutfit.member.service;

import java.util.Optional;
import com.ara.todayoutfit.member.model.Member;

public interface MemberService {

    Member save(Member user);

    void delete(Member user);

    Optional<Member> findById(String id);

}
