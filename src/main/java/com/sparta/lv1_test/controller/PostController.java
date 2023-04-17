package com.sparta.lv1_test.controller;

import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    //
    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("index");

    }

    //Create
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto){
        return postService.createPost(requestDto);
    }
    //Read
    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return postService.getPosts();
    }
    //Update
    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }
    //Delete
    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id){
        return postService.deletePost(id);
    }


}
