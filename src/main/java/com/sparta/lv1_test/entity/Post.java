package com.sparta.lv1_test.entity;


import com.sparta.lv1_test.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;


    // dto에 담아서 반환
    public Post (String username,PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = username;
        this.content = postRequestDto.getContent();
    }

    // dto에 담아서 반환
    public void updatePost(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }
}
