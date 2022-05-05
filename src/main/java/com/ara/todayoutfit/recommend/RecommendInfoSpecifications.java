package com.ara.todayoutfit.recommend;

import com.ara.todayoutfit.admin.Admin;
import org.springframework.data.jpa.domain.Specification;

public class RecommendInfoSpecifications {

    public static Specification<RecommendInfo> findMin(double temp) {

        return (Specification<RecommendInfo>) ((root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("minTemp"), temp)
        );

    }

    public static Specification<RecommendInfo> findMax(double temp) {

        return (Specification<RecommendInfo>) ((root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("maxTemp"), temp)
        );
    }

}
