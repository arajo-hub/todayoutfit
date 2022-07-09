package com.ara.todayoutfit.board.repository;

import com.ara.todayoutfit.board.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    Page<Post> findByLocation(String location, String today, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

}
