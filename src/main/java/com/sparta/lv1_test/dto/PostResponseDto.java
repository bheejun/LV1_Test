package com.sparta.lv1_test.dto;

import com.sparta.lv1_test.entity.Comment;
import com.sparta.lv1_test.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.comments = post.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
