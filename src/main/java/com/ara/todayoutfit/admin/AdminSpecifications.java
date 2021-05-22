package com.ara.todayoutfit.admin;

import com.ara.todayoutfit.board.Post;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecifications {

    public static Specification<Admin> findOneAdmin(String name) {

        return (Specification<Admin>) ((root, query, builder) ->
            builder.equal(root.get("name"), name)
        );

    }

}
