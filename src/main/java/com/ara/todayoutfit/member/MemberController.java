package com.ara.todayoutfit.member;

import com.ara.todayoutfit.board.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

@Controller
public class MemberController {

    @Autowired
    private PostRepository repository;

    @RequestMapping("/")
    public String jspCheck() {
        System.out.println("WebController.jspCheck");
        return "member/index";
    }

    @RequestMapping(value = "/board/list.action", method={RequestMethod.GET})
    public String showList(HttpServletRequest request, Model model) {
        String location = request.getParameter("location");
        Date today = TimeService.getTodayToDate();
        Date now = TimeService.getNow();

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

        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "writedate").descending());

        Page<Post> totalPosts = repository.findAll(PostSpecifications.equalToSpecificLocation(location)
                                                                .and(PostSpecifications.findNotDeclared())
                                                                .and(PostSpecifications.findAllTodayPosts(today, now)), pageRequest);

        for (Post totalPost : totalPosts) {
            System.out.println("totalPost.getContent() = " + totalPost.getContent());
        }
        
        int nowPage = totalPosts.getPageable().getPageNumber(); // 현재 페이지
        int totalPages = totalPosts.getTotalPages(); // 총 페이지 수
        int pageBlock = 10;
        int startPage = ((nowPage)/pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        endPage = totalPages < endPage? totalPages:endPage;

        model.addAttribute("location", location);
        model.addAttribute("totalPosts", totalPosts);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/list";
    }

    @RequestMapping(value = "/board/add.action", method={RequestMethod.POST})
    public String addPost(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String location = request.getParameter("nowLocation");
        String content = request.getParameter("content");

        Post post = new Post();

        post.setLocation(location);
        post.setContent(content);

        repository.save(post);

        redirectAttributes.addAttribute("location", location);

        return "redirect:/board/list.action";
    }

    @RequestMapping(value = "/board/recommendup.action", method = RequestMethod.GET)
    public void recommendUp(HttpServletResponse resp, String seq) throws IOException {
        System.out.println("WebController.recommendUp");

        Post post = repository.getOne(Long.parseLong(seq));
        post.setRecommendcnt(post.getRecommendcnt()+1);
        repository.saveAndFlush(post);

        PrintWriter writer = resp.getWriter();
        writer.print(post.getRecommendcnt());
        writer.close();

    }

    @RequestMapping(value = "/board/declare.action", method = RequestMethod.GET)
    public void declare(HttpServletResponse resp, String seq) throws IOException {

        Post post = repository.getOne(Long.parseLong(seq));
        post.setDeclare(Declare.DECLARED);
        repository.saveAndFlush(post);

    }

}
