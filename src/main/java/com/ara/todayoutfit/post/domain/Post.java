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
    private String content;

    @Column
    private String location;

    @Column(name="like_cnt")
    private Long likeCount;

    @Column(name="declared_yn")
    private boolean declaredYn;

    @Column(name="write_date")
    @CreatedDate
    private LocalDateTime writeDate;

    @Builder
    public Post(String content, String location, long likeCount, boolean declaredYn, LocalDateTime writeDate) {
        this.content = content;
        this.location = location;
        this.likeCount = likeCount;
        this.declaredYn = declaredYn;
        this.writeDate = writeDate;
    }

    public PostShow toPostShow() {
        return PostShow.builder()
                .postId(postId)
                .content(content)
                .location(location)
                .recommendCnt(likeCount)
                .declaredYn(declaredYn)
                .writeDate(writeDate)
                .build();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
