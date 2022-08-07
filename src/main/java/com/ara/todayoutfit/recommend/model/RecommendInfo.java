package com.ara.todayoutfit.recommend.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="recommendinfo")
public class RecommendInfo implements Serializable {

    @Id
    @Column(name="recommendinfo_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendInfoSeq;

    @Column(name="max_temp")
    private Double maxTemp;

    @Column(name="min_temp")
    private Double minTemp;

    @Column
    private String message;

}
