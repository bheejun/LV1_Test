package com.sparta.lv1_test.repository;

import com.sparta.lv1_test.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //생성된 날짜 기준으로 내림차순으로 정렬
    List<Post> findAllByOrderByCreatedAtDesc();

}
