package com.ara.todayoutfit.admin;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * AOP 구현하여 관리자페이지 접근을 막는 역할을 담당
 */

@Slf4j
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

        if (session.getAttribute("name") == null
            || !session.getAttribute("name").equals("admin")) {

            try {

                log.info("[{}] Access Denied",
                        Thread.currentThread().getStackTrace()[1].getMethodName());

                response.setCharacterEncoding("UTF-8");

                PrintWriter writer = response.getWriter();
                writer.println("<html><head><meta charset='utf-8'></head><body>");
                writer.println("<script>alert('접근할 수 없습니다.'); location.href='/';</script>");

                writer.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }

}
