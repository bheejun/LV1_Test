package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.jwt.JwtUtil;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;




    //save 기능을 이용해서 db에 Entity에서 Dto에 담아서 반환된 정보를 저장한다
    @Transactional
    public Post createPost(PostRequestDto requestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("id does not exist")
        );
        Post post = new Post(requestDto);
        post.setAuthor(user.getUsername());
        postRepository.save(post);
        System.out.println("success");

        return post;

    }
    // 생성된 날짜 기준으로 내림차순으로 정렬해서 리스트에 담아서 반환해줌
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id does not exist")
        );

    }

    //id기준으로 db에서 정보 찾아서 검사해서 없으면 메세지 띄우고 있으면 비밀번호 체크해서 맞으면 업데이트, 틀리면 예외처리한 메세지 출력
    public Post updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        String username = jwtUtil.getUserInfoFromToken(token).getSubject();

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id does not exist")
        );

        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You can only update your own posts");
        }

        post.updatePost(requestDto);
        return postRepository.save(post);
    }

    public Long deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        String username = jwtUtil.getUserInfoFromToken(token).getSubject();

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id does not exist")
        );

        if (!post.getAuthor().equals(username)) {
            throw new IllegalArgumentException("You can only delete your own posts");
        }

        postRepository.deleteById(id);
        return id;
    }


}
