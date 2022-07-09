package com.ara.todayoutfit.admin.repository;

import com.ara.todayoutfit.admin.model.Admin;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecifications {

    public static Specification<Admin> findOneAdmin(String name) {

        return (Specification<Admin>) ((root, query, builder) ->
            builder.equal(root.get("name"), name)
        );

    }

}
