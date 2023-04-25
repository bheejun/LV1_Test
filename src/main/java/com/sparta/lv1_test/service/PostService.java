package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.jwt.JwtUtil;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.repository.UserRepsotiry;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepsotiry userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Post createPost(PostRequestDto requestDto, HttpServletRequest request){
        // 유효한 토큰값 username에 넣기
        Post post = new Post(tockencheck(request).getSubject(),requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts() { //게시물 생성순으로 전체조회
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id")
        );
    }


    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request){

        //유효한 토큰값 체크
        Claims claims = tockencheck(request);
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 id")
        );
        if(claims.getSubject().equals(post.getUsername())){
            post.updatePost(requestDto);
        }
        return post.getId();
    }


    @Transactional
    public Long deletePost(Long id, HttpServletRequest request) {
        Claims claims =  tockencheck(request);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id")
        );

        if(claims.getSubject().equals(post.getUsername())){
            postRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("삭제권한이 없습니다");
        }
        return id;
    }


    public Claims tockencheck(HttpServletRequest request){
        //토큰값 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        //토큰값이 null이 아니고 유효한 토큰값인지 확인
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }
        }
        return jwtUtil.getUserInfoFromToken(token);
    }
}
