package com.sparta.lv1_test.service;


import com.sparta.lv1_test.dto.PostRequestDto;
import com.sparta.lv1_test.entity.Post;
import com.sparta.lv1_test.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    //save 기능을 이용해서 db에 Entity에서 Dto에 담아서 반환된 정보를 저장한다
    public Post createPost(PostRequestDto requestDto){
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }
    // 생성된 날짜 기준으로 내림차순으로 정렬해서 리스트에 담아서 반환해줌
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id")
        );

    }

    //id기준으로 db에서 정보 찾아서 검사해서 없으면 메세지 띄우고 있으면 비밀번호 체크해서 맞으면 업데이트, 틀리면 예외처리한 메세지 출력
    public Long updatePost(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 id")
        );

        if(!post.checkPassword(requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호 틀림");
        }
        post.updatePost(requestDto);
        return post.getId();

    }
    //id기준으로 db에서 정보 찾아서 검사해서 없으면 메세지 띄우고, 있으면 비밀번호 체크해서 맞으면 삭제, 틀리면 예외처리 메세지
    public Long deletePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 id")
        );

        // requestDto로부터 받은 비밀번호로 비밀번호 확인
        if (!post.checkPassword(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }
        postRepository.deleteById(id);
        return id;
    }


}
