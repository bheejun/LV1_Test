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

    @Column(nullable = false)
    private String password;

    // dto에 담아서 반환
    public Post (PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();
    }
    // dto에 담아서 반환
    public void updatePost(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.content = postRequestDto.getContent();
    }
    // 비밀번호 체크
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
