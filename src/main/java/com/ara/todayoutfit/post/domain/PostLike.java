package com.ara.todayoutfit.post.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="postlike")
@NoArgsConstructor
public class PostLike {

    @Id
    @Column(name="postlike_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeSeq;

    @Column
    private Long postSeq;

    @Column
    private String ip;

    @Builder
    public PostLike(Long postSeq, String ip) {
        this.postSeq = postSeq;
        this.ip = ip;
    }

}
