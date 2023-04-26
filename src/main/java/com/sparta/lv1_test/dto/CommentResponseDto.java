package com.sparta.lv1_test.dto;


import com.sparta.lv1_test.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;


    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
    }
}
