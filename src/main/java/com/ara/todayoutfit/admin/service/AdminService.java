package com.ara.todayoutfit.admin.service;

import com.ara.todayoutfit.admin.model.Admin;
import com.ara.todayoutfit.common.BaseResult;

import javax.servlet.http.HttpSession;

public interface AdminService {
    BaseResult login(HttpSession session, Admin admin);
}
