package com.ara.todayoutfit.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository repository;

    @RequestMapping(value = "/test")
    public String findAll() {
        List<Post> posts = repository.findAll();

        System.out.println(posts.size());

        for (Post post : posts) {
            System.out.println("post = " + post);
        }

        return "test";
    }

}
