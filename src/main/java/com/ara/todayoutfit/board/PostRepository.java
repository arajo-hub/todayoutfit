package com.ara.todayoutfit.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

//    Page<Post> findByLocation(String location, Pageable pageable);
    Page<Post> findByLocation(String location, String today, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

}
