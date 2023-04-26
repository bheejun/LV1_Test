package com.sparta.lv1_test.controller;

import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.dto.PostResponseDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //Create

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        PostResponseDto post = postService.createPost(requestDto, request);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }


    //Read
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> posts = postService.getPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto post = postService.getPost(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Update
    @PutMapping("/api/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        PostResponseDto post = postService.updatePost(id, requestDto, request);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id, HttpServletRequest request) {
        Long deletedPostId = postService.deletePost(id, request);
        return new ResponseEntity<>(deletedPostId, HttpStatus.OK);
    }
}

