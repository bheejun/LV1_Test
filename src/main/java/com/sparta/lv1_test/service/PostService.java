package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.dto.PostResponseDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.repository.PostRepository;
import com.sparta.lv1_test.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GetInformation getInformation;

    //save 기능을 이용해서 db에 Entity에서 Dto에 담아서 반환된 정보를 저장한다
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        String username = getInformation.getUsernameFromToken(request);
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("id does not exist")
        );
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
                () -> new IllegalArgumentException("id does not exist")
        );
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String username = getInformation.getUsernameFromToken(request);
        System.out.println("Username: "+ username);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id does not exist")
        );

        boolean isAdmin = getInformation.isAdmin(username);
        System.out.println("Is admin: " + isAdmin);

        if (!post.getAuthor().equals(username) && !getInformation.isAdmin(username)) {
            throw new IllegalArgumentException("You can only update your own posts or must be an admin");
        }

        post.updatePost(requestDto);
       Post updatedPost= postRepository.saveAndFlush(post);

        return new PostResponseDto(updatedPost);
    }

    public Long deletePost(Long id, HttpServletRequest request) {
        String username = getInformation.getUsernameFromToken(request);

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id does not exist")
        );

        if (!post.getAuthor().equals(username) && !getInformation.isAdmin(username)) {
            throw new IllegalArgumentException("You can only update your own posts or must be an admin");
        }

        postRepository.deleteById(id);
        return id;
    }
}





