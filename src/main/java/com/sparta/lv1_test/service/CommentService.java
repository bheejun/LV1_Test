package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.CommentRequestDto;
import com.sparta.lv1_test.dto.CommentResponseDto;
import com.sparta.lv1_test.entity.Comment;
import com.sparta.lv1_test.entity.Post;

import com.sparta.lv1_test.repository.CommentRepository;
import com.sparta.lv1_test.repository.PostRepository;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    private final GetInformation getInformation;



    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, HttpServletRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));

        String username = getInformation.getUsernameFromToken(request);
        Comment comment = new Comment(requestDto.getContent(), username);
        comment.setPost(post);
        post.getComments().add(comment);
        Comment savedComment= commentRepository.saveAndFlush(comment);
        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
        String username = getInformation.getUsernameFromToken(request);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        if (!comment.getAuthor().equals(username) && !getInformation.isAdmin(username)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다");
        }

        comment.setContent(requestDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment);
    }

    @Transactional
    public Long deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
        String username = getInformation.getUsernameFromToken(request);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        if (!comment.getAuthor().equals(username) && !getInformation.isAdmin(username)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다");
        }

        commentRepository.delete(comment);
        return comment.getId();
    }


}
