package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.board.Declare;
import com.ara.todayoutfit.board.Post;
import com.ara.todayoutfit.board.PostRepository;
import com.ara.todayoutfit.board.PostSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/login.action", method = {RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        log.info("[{}]",
                Thread.currentThread().getStackTrace()[1].getMethodName());
        return "admin/login";
    }

    @RequestMapping(value = "/login.action", method = {RequestMethod.POST})
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model){

        String name = request.getParameter("name");
        String pw = request.getParameter("pw");

        Optional<Admin> attemptedToLogin = adminRepository.findOne(AdminSpecifications.findOneAdmin(name));

        if (attemptedToLogin.isPresent()) {

            Admin loggedInAdmin = attemptedToLogin.get();

            if (pw.equals(loggedInAdmin.getPw())) {

                session.setAttribute("name", loggedInAdmin.getName());

                log.info("[{}] Logged in = {}",
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        loggedInAdmin.getId(), loggedInAdmin.getName(), loggedInAdmin.getPw());

                return "redirect:/admin/board/list.action";

            } else {

                log.info("[{}] Password not equal",
                        Thread.currentThread().getStackTrace()[1].getMethodName());

                return "admin/login";
            }

        } else {

            log.info("[{}] Id not found",
                    Thread.currentThread().getStackTrace()[1].getMethodName());

            // 찾는 아이디가 없음
            return "admin/login";
        }
    }

    @RequestMapping(value = "/board/list.action", method = {RequestMethod.GET})
    public String adminShowList(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

        int page;
        // 테스트용 코드
        if (request.getParameter("page") == null) {
            page = 1;
        } else {

            if (Integer.parseInt(request.getParameter("page")) <= 0) {
                page = 1;
            } else {
                page = Integer.parseInt(request.getParameter("page"));
            }
        }

        // 페이지네이션해줄 PageRequest 생성
        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "writedate").descending());

        Page<Post> totalPosts = postRepository.findAll(pageRequest);

        int nowPage = totalPosts.getPageable().getPageNumber(); // 현재 페이지
        int totalPages = totalPosts.getTotalPages(); // 총 페이지 수
        int pageBlock = 10;
        int startPage = ((nowPage)/pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        endPage = totalPages < endPage? totalPages:endPage;

        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        log.info("[{}] totalPosts = {}, startPage = {}, endPage = {}",
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                totalPages, startPage, endPage);

        return "admin/list";
    }

    @RequestMapping(value = "/board/del.action", method = {RequestMethod.GET})
    public String adminDelPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Long id = Long.parseLong(request.getParameter("id"));

        Post deletedPost = postRepository.getOne(id);

        postRepository.delete(deletedPost);

        log.info("[{}] Delete completed",
                Thread.currentThread().getStackTrace()[1].getMethodName());

        return "redirect:/admin/board/list.action";
    }

    @RequestMapping(value = "/board/canceldeclare.action", method = {RequestMethod.GET})
    public void adminCancelDeclare(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Post cancel = postRepository.getOne(id);
        cancel.setDeclared(Declare.NOT_DECLARED);
        postRepository.saveAndFlush(cancel);

        PrintWriter writer = response.getWriter();
        writer.print(cancel.getDeclared());
        writer.close();

        log.info("[{}] Declare canceled",
                Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @RequestMapping(value = "/logout.action", method = {RequestMethod.GET})
    public String adminLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();

        log.info("[{}] Logout");
        return "redirect:/admin/login.action";
    }

}
