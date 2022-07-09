package com.ara.todayoutfit.board.repository;

import com.ara.todayoutfit.board.model.Declare;
import com.ara.todayoutfit.board.model.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PostSpecifications {

    public static Specification<Post> equalToSpecificLocation(String location) {

        return (Specification<Post>) ((root, query, builder) ->
            builder.like(root.get("location"), "%" + location + "%")
        );

    }

    public static Specification<Post> findAllTodayPosts(LocalDateTime now) {

        return (Specification<Post>) ((root, query, builder) ->
                builder.between(root.get("writeDate"), now.toLocalDate().atStartOfDay(), now)
        );

    }

    public static Specification<Post> findNotDeclared() {

        return (Specification<Post>) ((root, query, builder) ->
                builder.equal(root.get("declared_yn"), Declare.NOT_DECLARED.getCode())
        );

    }
}
