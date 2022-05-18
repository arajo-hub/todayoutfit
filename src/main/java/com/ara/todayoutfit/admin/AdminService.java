package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.common.BaseResult;

import javax.servlet.http.HttpSession;

public interface AdminService {
    BaseResult login(HttpSession session, Admin admin);
}
