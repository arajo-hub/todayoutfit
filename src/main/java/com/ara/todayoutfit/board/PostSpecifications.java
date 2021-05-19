package com.ara.todayoutfit.board;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                builder.equal(root.get("declare"), Declare.NOT_DECLARED)
        );

    }
}
