package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.board.Post;
import com.ara.todayoutfit.board.PostRepository;
import com.ara.todayoutfit.board.PostSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/login.action", method = {RequestMethod.GET})
    public String login(Model model) {

        List<Admin> all = adminRepository.findAll();

        model.addAttribute("all", all);

        return "admin/login";
    }

    @RequestMapping(value = "/login.action", method = {RequestMethod.POST})
    public String login(HttpServletRequest request, HttpSession session, Model model) {

        String name = request.getParameter("name");
        String pw = request.getParameter("pw");

        Optional<Admin> attemptedToLogin = adminRepository.findOne(AdminSpecifications.findOneAdmin(name));

        if (attemptedToLogin.isPresent()) {

            Admin loggedInAdmin = attemptedToLogin.get();

            if (pw.equals(loggedInAdmin.getPw())) {

                session.setAttribute("id", loggedInAdmin.getId());

                model.addAttribute("admin", loggedInAdmin);

                return "admin/list";
            } else {
                return "admin/login";
            }

        } else {
            // 찾는 아이디가 없음
            return "admin/login";
        }
    }

    @RequestMapping(value = "/board/list.action", method = {RequestMethod.GET})
    public String showList(Model model) {

        List<Post> posts = postRepository.findAll();

        model.addAttribute("posts", posts);

        return "admin/list";
    }

}
