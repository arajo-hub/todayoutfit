package com.ara.todayoutfit;

import com.ara.todayoutfit.board.Post;
import com.ara.todayoutfit.board.PostRepository;
import com.ara.todayoutfit.board.PostSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

        Specification<Post> postSpecification = Specification.where(PostSpecifications.equalToSpecificLocation(location));

        List<Post> posts = repository.findAll(postSpecification);

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

}
