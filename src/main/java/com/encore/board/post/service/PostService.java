package com.encore.board.post.service;

import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post postCreate(PostSaveReqDto postSaveReqDto) {
        Post post = new Post(postSaveReqDto.getTitle(), postSaveReqDto.getContents());
        postRepository.save(post);
        return post;
    }

    public Post findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public List<PostListResDto> postList() {
        List<Post> postList = postRepository.findAll();
        List<PostListResDto> postListResDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
            postListResDtoList.add(postListResDto);
        }
        return postListResDtoList;
    }

    public PostDetailResDto PostDetail(Long id) {
        Post post = this.findById(id);
        PostDetailResDto postDetailResDto = new PostDetailResDto(post.getId(), post.getTitle(), post.getContents(), post.getCreatedTime());
        return postDetailResDto;
    }

    public Post postUpdate(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = this.findById(id);
        post.postUpdate(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
        return post;
    }

    public void postDelete(Long id) {
        postRepository.delete(this.findById(id));
    }
}
