package com.ara.todayoutfit.recommend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name="recommendinfo")
public class RecommendInfo implements Serializable {

    @Id
    @Column(name="recommendinfo_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendInfoSeq;

    @Column(name="max_temp")
    private Integer maxTemp;

    @Column(name="min_temp")
    private Integer minTemp;

    @Column
    @Size(max = 30)
    private String message;

    @Builder
    public RecommendInfo(Integer maxTemp, Integer minTemp, String message) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.message = message;
    }

    public RecommendInfoShow.RecommendInfoShowBuilder toRecommendInfoShow() {
        return RecommendInfoShow.builder()
                .message(message)
                .maxTemp(maxTemp)
                .minTemp(minTemp);
    }

}
