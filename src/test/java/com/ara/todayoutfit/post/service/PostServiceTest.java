package com.ara.todayoutfit.post.service;

import com.ara.todayoutfit.post.repository.PostLikeRepository;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
        postLikeRepository.deleteAll();
    }

    @Test
    @DisplayName("% 검색")
    void findByPercentageSymbol() {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("옷차림 글 테스트1")
                .location("광진구")
                .build();
        postService.savePost(request);

        PostSearch searchParam = PostSearch.builder()
                .page(1)
                .size(20)
                .location("%")
                .build();

        String ip = "192.168.1.100";

        PageResult pageResult = postService.findPostByLocation(searchParam, ip);

        assertTrue(pageResult.getList().getSize() > 0);
    }
//
//    @Test
//    @DisplayName("글 목록 조회")
//    void findAll() {
//        List<Post> posts = List.of(Post.builder()
//                        .content("옷차림 글 테스트1")
//                        .location("광진구")
//                        .recommendCnt(1L)
//                        .declaredYn(false)
//                        .writeDate(LocalDateTime.now())
//                        .build(),
//                Post.builder()
//                        .content("옷차림 글 테스트2")
//                        .location("광진구")
//                        .recommendCnt(1L)
//                        .declaredYn(false)
//                        .writeDate(LocalDateTime.now())
//                        .build());
//        postService.saveAll(posts);
//
//        PostSearch searchParam = PostSearch.builder()
//                        .page(1)
//                        .size(10)
//                        .location(null)
//                        .build();
//
//        PageResult pageResult = postService.findAll(searchParam);
//
//        assertEquals(posts.size(), pageResult.getList().getNumberOfElements());
//    }
//
//    @Test
//    @DisplayName("위치명으로 글 검색")
//    void findByLocation() {
//        List<Post> posts = List.of(Post.builder()
//                        .content("옷차림 글 테스트1")
//                        .location("광진구")
//                        .recommendCnt(1L)
//                        .declaredYn(false)
//                        .writeDate(LocalDateTime.now())
//                        .build(),
//                Post.builder()
//                        .content("옷차림 글 테스트2")
//                        .location("강남구")
//                        .recommendCnt(1L)
//                        .declaredYn(false)
//                        .writeDate(LocalDateTime.now())
//                        .build());
//        postService.saveAll(posts);
//
//        String location = "광진구";
//        PostSearch searchParam = PostSearch.builder()
//                .page(1)
//                .size(10)
//                .location(location)
//                .build();
//
//        String ip = "192.168.1.100";
//
//        PageResult pageResult = postService.findByLocation(searchParam, ip);
//
//        List<Post> filtered = posts.stream().filter(p -> p.getLocation().equals(location)).collect(Collectors.toList());
//
//        assertEquals(filtered.size(), pageResult.getList().getNumberOfElements());
//    }
//
//    @Test
//    @DisplayName("글 삭제")
//    void delete() {
//        Post post = Post.builder()
//                .content("삭제테스트")
//                .location("광진구")
//                .recommendCnt(1L)
//                .declaredYn(false)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//        postRepository.deleteBySeq(post.getPostSeq());
//        List<Post> all = postRepository.findAll();
//
//        assertEquals(0, all.size());
//    }
//
//    @Test
//    @DisplayName("글 신고 취소")
//    void cancelDeclare() {
//        Post post = Post.builder()
//                .content("삭제테스트")
//                .location("광진구")
//                .recommendCnt(1L)
//                .declaredYn(true)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//
//        postService.cancelDeclare(post.getPostSeq());
//
//        Optional<Post> postFindBySeq = postRepository.findBySeq(post.getPostSeq());
//        Post result = new Post();
//        if (postFindBySeq.isPresent()) {
//            result = postFindBySeq.get();
//        }
//
//        assertEquals(false, result.isDeclaredYn());
//    }
//
//    @Test
//    @DisplayName("글 저장")
//    void save() {
//        Post post = Post.builder()
//                .content("저장테스트")
//                .location("광진구")
//                .recommendCnt(1L)
//                .declaredYn(false)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//
//        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
//
//        assertEquals(1, postBySeq.stream().count());
//    }
//
//    @Test
//    @DisplayName("50자 이상 글 저장")
//    void saveLongContent() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Post post = Post.builder()
//                    .content("50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상")
//                    .location("광진구")
//                    .recommendCnt(1L)
//                    .declaredYn(false)
//                    .writeDate(LocalDateTime.now())
//                    .build();
//            postService.save(post);
//        });
//    }
//
//    @Test
//    @DisplayName("내용없는 글 저장")
//    void saveNoLengthContent() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Post post = Post.builder()
//                    .content("")
//                    .location("광진구")
//                    .recommendCnt(1L)
//                    .declaredYn(false)
//                    .writeDate(LocalDateTime.now())
//                    .build();
//            postService.save(post);
//        });
//    }
//
//    @Test
//    @DisplayName("15자 이상 지역으로 글 저장")
//    void saveLongLocation() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Post post = Post.builder()
//                    .content("저장테스트")
//                    .location("광진구광진구광진구광진구광진구광진구")
//                    .recommendCnt(1L)
//                    .declaredYn(false)
//                    .writeDate(LocalDateTime.now())
//                    .build();
//            postService.save(post);
//        });
//    }
//
//    @Test
//    @DisplayName("지역 입력없이 글 저장")
//    void saveNoLengthLocation() {
//        assertThrows(ConstraintViolationException.class, () -> {
//            Post post = Post.builder()
//                    .content("저장테스트")
//                    .location("")
//                    .recommendCnt(1L)
//                    .declaredYn(false)
//                    .writeDate(LocalDateTime.now())
//                    .build();
//            postService.save(post);
//        });
//    }
//
//    @Test
//    @DisplayName("좋아요 버튼 클릭")
//    void recommend() {
//        long init = 0;
//        Post post = Post.builder()
//                .content("삭제테스트")
//                .location("광진구")
//                .recommendCnt(init)
//                .declaredYn(false)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//
//        String ip = "192.168.1.100";
//
//        postService.recommend(post.getPostSeq(), ip);
//
//        Optional<PostLike> postLike = postLikeRepository.findByPostSeqAndIp(post.getPostSeq(), ip);
//        Optional<Post> findBySeq = postRepository.findBySeq(post.getPostSeq());
//        Post saved = findBySeq.isPresent() ? findBySeq.get() : null;
//
//        assertTrue(postLike.isPresent());
//        assertEquals(init + 1, saved.getRecommendCnt().longValue());
//    }
//
//    @Test
//    @DisplayName("좋아요 취소")
//    void recommendAlreadyRecommendedPost() {
//        long init = 1;
//        Post post = Post.builder()
//                .content("삭제테스트")
//                .location("광진구")
//                .declaredYn(false)
//                .recommendCnt(init)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//
//        String ip = "192.168.1.100";
//
//        PostLike postLike = PostLike.builder()
//                .postSeq(post.getPostSeq())
//                .ip(ip)
//                .build();
//        postLikeRepository.save(postLike);
//
//        BaseResult result = postService.recommend(post.getPostSeq(), ip);
//
//        Optional<PostLike> postLikeFindBySeq = postLikeRepository.findBySeq(postLike.getPostLikeSeq());
//        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
//        Post saved = postBySeq.isPresent() ? postBySeq.get() : null;
//
//        assertEquals(ResponseCode.SUCCESS, result.getResponseCode());
//        assertTrue(postLikeFindBySeq.isEmpty());
//        assertEquals(init - 1, saved.getRecommendCnt().longValue());
//    }
//
//    @Test
//    @DisplayName("글 신고")
//    void declare() {
//        Post post = Post.builder()
//                .content("삭제테스트")
//                .location("광진구")
//                .recommendCnt(0)
//                .declaredYn(false)
//                .writeDate(LocalDateTime.now())
//                .build();
//        postService.save(post);
//
//        postService.declare(post.getPostSeq());
//
//        Optional<Post> postBySeq = postRepository.findBySeq(post.getPostSeq());
//        Post result = new Post();
//        if (postBySeq.isPresent()) {
//            result = postBySeq.get();
//        }
//        assertEquals(true, result.isDeclaredYn());
//
//    }
}