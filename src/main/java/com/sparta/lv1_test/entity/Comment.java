package com.sparta.lv1_test.entity;

import com.sparta.lv1_test.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private  String author;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post){
        this.post = post;
    }

    public Comment(String content, String author){
        this.content = content;
        this.author = author;

    }

    public void updateComment(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }


}
