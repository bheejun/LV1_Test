package com.sparta.lv1_test.controller;

import com.sparta.lv1_test.dto.CommentRequestDto;
import com.sparta.lv1_test.dto.CommentResponseDto;
import com.sparta.lv1_test.entity.Comment;
import com.sparta.lv1_test.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, Authentication authentication) {
        CommentResponseDto comment = commentService.createComment(postId, requestDto, authentication);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    // Update a comment
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, Authentication authentication) {
        CommentResponseDto updatedComment = commentService.updateComment(postId, commentId, requestDto, authentication);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Authentication authentication) {
        Long deletedCommentId = commentService.deleteComment(postId, commentId, authentication);
        return new ResponseEntity<>(deletedCommentId, HttpStatus.OK);
    }
}


