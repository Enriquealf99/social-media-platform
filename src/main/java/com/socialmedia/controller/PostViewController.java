package com.socialmedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostViewController {

    @GetMapping
    public String showPostPage() {
        return "post";  // This will render the post.html file
    }
}
