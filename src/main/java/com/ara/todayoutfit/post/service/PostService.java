package com.ara.todayoutfit.post.service;

import com.ara.todayoutfit.post.domain.Post;
import com.ara.todayoutfit.post.domain.PostLike;
import com.ara.todayoutfit.post.repository.PostLikeRedisRepository;
import com.ara.todayoutfit.post.repository.PostLikeRepository;
import com.ara.todayoutfit.post.repository.PostRedisRepository;
import com.ara.todayoutfit.post.request.PostLikeMultiSearch;
import com.ara.todayoutfit.post.request.PostLikeSearch;
import com.ara.todayoutfit.post.request.PostSearch;
import com.ara.todayoutfit.post.response.PostShow;
import com.ara.todayoutfit.post.repository.PostRepository;
import com.ara.todayoutfit.post.request.PostCreateRequest;
import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.PageResponse;
import com.ara.todayoutfit.common.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    private final PostRedisRepository postRedisRepository;

    private final PostLikeRedisRepository postLikeRedisRepository;

    public PageResponse<PostShow> findAll(PostSearch postSearch) {
        Page<PostShow> all = postRepository.findAll(postSearch);
        return PageResponse.<PostShow>builder()
                .list(all)
                .build();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findBySeq(Long id) {
        return postRepository.findBySeq(id);
    }

    /**
     * 위치 기반으로 게시판 글 조회
     * @param postSearch
     * @param ip
     * @return
     */
    public PageResponse<PostShow> findPostByLocation(PostSearch postSearch, String ip) {
        Pageable pageable = PageRequest.of(postSearch.getPage() - 1, postSearch.getSize());
        List<Post> allPostsByLocation = postRepository.findPostByLocation(postSearch, pageable);
        Long count = postRepository.getCount(postSearch.getLocation());

        List<Long> postSeqs = new ArrayList<>();
        allPostsByLocation.forEach(p -> postSeqs.add(p.getPostId()));
        PostLikeMultiSearch search = PostLikeMultiSearch.builder().postIds(postSeqs).ip(ip).build();
        List<Long> likedIds = postLikeRepository.getPostLikes(search);

        List<PostShow> postShows = new ArrayList<PostShow>();
        allPostsByLocation.forEach(p -> {
            PostShow postShow = p.toPostShow();
            postShow.setLikedYn(likedIds.contains(p.getPostId()));
            postShows.add(postShow);
        });

        Page<PostShow> postShowPages = new PageImpl<>(postShows, pageable, count);
        return new PageResponse<PostShow>(postShowPages);
    }

    /**
     * 게시글 등록
     * @param request
     * @return
     */
    public BaseResult savePost(PostCreateRequest request) {
        Post post = request.toPost();
        Post save = postRepository.save(post);
        return post.equals(save) ? new BaseResult(ResultCode.SUCCESS) : new BaseResult(ResultCode.FAIL);
    }

    public BaseResult cancelDeclare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.cancelDeclare(seq);
        return result;
    }

    public BaseResult saveAll(List<Post> posts) {
        List<Post> saveAll = postRepository.saveAll(posts);
        return posts.equals(saveAll) ? new BaseResult(ResultCode.SUCCESS) : new BaseResult(ResultCode.FAIL);
    }

    /**
     * 게시글 좋아요 기능
     * @param seq
     * @param ip
     * @return
     */
    public BaseResult likePost(Long seq, String ip) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        Optional<Post> postBySeq = postRepository.findBySeq(seq);
        if (!postBySeq.isPresent()) {
            return new BaseResult(ResultCode.DB_NOT_FOUND_DATA);
        }
        Post targetPost = postBySeq.get();
        boolean isExecuted = postLikeRedisRepository.likePost(seq, ip);
        synchronized (this) {
            if (isExecuted) {
                PostLike like = PostLike.builder().postSeq(seq).ip(ip).build();
                postLikeRepository.insertPostLike(like);
                targetPost.increaseLikeCount();
            } else {
                if (targetPost.getLikeCount() > 0) {
                    isExecuted = postLikeRedisRepository.deleteLikePost(seq, ip);
                    PostLikeSearch search = PostLikeSearch.builder().postId(seq).ip(ip).build();
                    postLikeRepository.deletePostLike(search);
                    targetPost.decreaseLikeCount();
                }
            }
        }
        if (!isExecuted) {
            result = new BaseResult(ResultCode.FAIL);
        }
        return result;
    }

    public BaseResult cancelRecommend(Long seq, String ip) {
//        postRepository.deleteSamePostSeqAdnIp(seq, ip);
//        postRepository.cancelRecommend(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public BaseResult declare(Long seq) {
        //결과
        BaseResult result = new BaseResult(ResultCode.SUCCESS);
        postRepository.declare(seq);
        return result;
    }

    public BaseResult delete(Long seq) {
        postRepository.deleteBySeq(seq);
        return new BaseResult(ResultCode.SUCCESS);
    }

    public BaseResult deleteAll() {
        postRepository.deleteAll();
        return new BaseResult(ResultCode.SUCCESS);
    }
}
