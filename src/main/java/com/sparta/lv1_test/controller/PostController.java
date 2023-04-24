package com.sparta.lv1_test.controller;

import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;
    //
    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("index");

    }

    //Create
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.createPost(requestDto, request);
    }
    //Read
    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id){
        return postService.getPost(id);
    }
    //Update
    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }






}
