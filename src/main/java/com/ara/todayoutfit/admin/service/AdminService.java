package com.ara.todayoutfit.admin.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.member.model.MemberSearch;

import javax.servlet.http.HttpSession;

public interface AdminService {
    BaseResult login(HttpSession session, MemberSearch admin);
}
