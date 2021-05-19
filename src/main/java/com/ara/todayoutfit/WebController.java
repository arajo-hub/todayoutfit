package com.ara.todayoutfit;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

@Controller
public class WebController {

    @Autowired
    private PostRepository repository;

    @RequestMapping("/")
    public String jspCheck() {
        System.out.println("WebController.jspCheck");
        return "index";
    }

    @RequestMapping(value = "/board/list.action", method={RequestMethod.GET})
    public String showList(HttpServletRequest request, Model model) {
        String location = request.getParameter("location");
        Date today = TimeService.getTodayToDate();
        Date now = TimeService.getNow();

        System.out.println("today = " + today);
        System.out.println("now = " + now);

        PageRequest pageRequest = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "writedate"));

//        Page<Post> selected = repository.findByLocation(location, pageRequest);
//        List<Post> posts = selected.getContent();

//        Page<Post> all = repository.findAll(pageRequest);
//        List<Post> posts = all.getContent();

//        Specification<Post> postSpecification = Specification.where(PostSpecifications.equalToSpecificLocation(location))
//                                                    .and(PostSpecifications.findAllTodayPosts(today, now));

        List<Post> posts = repository.findAll(PostSpecifications.equalToSpecificLocation(location)
                                                                .and(PostSpecifications.findNotDeclared())
                                                                .and(PostSpecifications.findAllTodayPosts(today, now)), new Sort(Sort.Direction.DESC, "writedate"));

        model.addAttribute("location", location);
        model.addAttribute("posts", posts);

        return "list";
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
