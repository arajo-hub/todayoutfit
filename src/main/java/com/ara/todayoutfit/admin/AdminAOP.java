package com.ara.todayoutfit.admin;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * AOP 구현하여 관리자페이지 접근을 막는 역할을 담당
 */

@Aspect
@Controller
public class AdminAOP {

    @Pointcut("execution(public * com.ara.todayoutfit.admin.*.admin*(..))")
    public void pc1() {
    }

    @Before("pc1()")
    public void checkAdmin(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        HttpServletResponse response = (HttpServletResponse) args[1];
        HttpSession session = (HttpSession) args[2];

        if (session.getAttribute("id") == null
            || session.getAttribute("id") != "adminara") {

            try {

                response.setCharacterEncoding("UTF-8");

                PrintWriter writer = response.getWriter();
                writer.println("<html><head><meta charset='utf-8'></head><body>");
                writer.println("<script>alert('접근할 수 없습니다.'); location.href='/';</script>");

                writer.close();

            } catch (Exception e) {

                System.out.println(e);

            }

        }

    }

}
