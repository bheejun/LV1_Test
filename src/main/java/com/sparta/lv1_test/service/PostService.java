package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.dto.PostResponseDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.entity.UserRoleEnum;
import com.sparta.lv1_test.exception.TokenInvalidException;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.repository.UserRepository;
import com.sparta.lv1_test.security.AuthenticationFacade;
import com.sparta.lv1_test.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
//    private final AuthenticationFacade authenticationFacade;

    //save 기능을 이용해서 db에 Entity에서 Dto에 담아서 반환된 정보를 저장한다
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Post post = new Post(requestDto);
        post.setAuthor(user.getUsername());
        postRepository.saveAndFlush(post);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostResponseDto getPost(Long id){
        Post post =  postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id 존재하지 않습니다")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 존재하지 않습니다")
        );

        if (!post.getAuthor().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다");
        }

        post.updatePost(requestDto);
        Post updatedPost= postRepository.saveAndFlush(post);

        return new PostResponseDto(updatedPost);
    }

    public Long deletePost(Long id, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 존재하지 않습니다")
        );

        if (!post.getAuthor().equals(user.getUsername()) && user.getRole() != UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다");
        }

        postRepository.deleteById(id);
        return id;
    }
}





