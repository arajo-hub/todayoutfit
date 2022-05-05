package com.ara.todayoutfit.recommend;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name="recommendinfo")
public class RecommendInfo {

    @Id
    @Column(name="recommendinfo_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendInfoSeq;

    @Column(name="max_temp")
    private double maxTemp;

    @Column(name="min_temp")
    private double minTemp;

    @Column
    private String message;

}
