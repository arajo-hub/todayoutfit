package com.ara.todayoutfit.post.domain;

import com.ara.todayoutfit.post.response.PostShow;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="post")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name="post_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    @Size(min = 1, max = 50)
    private String content;

    @Column
    @Size(min = 1, max = 15)
    private String location;

    @Column(name="recommend_cnt")
    private Long recommendCnt;

    @Column(name="declared_yn")
    private boolean declaredYn;

    @Column(name="write_date")
    @CreatedDate
    private LocalDateTime writeDate;

    @Builder
    public Post(String content, String location, long recommendCnt, boolean declaredYn, LocalDateTime writeDate) {
        this.content = content;
        this.location = location;
        this.recommendCnt = recommendCnt;
        this.declaredYn = declaredYn;
        this.writeDate = writeDate;
    }

    public PostShow toPostShow() {
        return PostShow.builder()
                .postId(postId)
                .content(content)
                .location(location)
                .recommendCnt(recommendCnt)
                .declaredYn(declaredYn)
                .writeDate(writeDate)
                .build();
    }

}
