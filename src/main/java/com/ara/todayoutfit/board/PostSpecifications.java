package com.ara.todayoutfit.board;

import org.springframework.data.jpa.domain.Specification;
import java.util.Date;

public class PostSpecifications {

    public static Specification<Post> equalToSpecificLocation(String location) {

        return (Specification<Post>) ((root, query, builder) ->
            builder.equal(root.get("location"), location)
        );

    }

    public static Specification<Post> findAllTodayPosts(Date today, Date now) {

        return (Specification<Post>) ((root, query, builder) ->
                builder.between(root.get("writedate"), today, now)
        );

    }

    public static Specification<Post> findNotDeclared() {

        return (Specification<Post>) ((root, query, builder) ->
                builder.equal(root.get("declared"), Declare.NOT_DECLARED)
        );

    }
}
