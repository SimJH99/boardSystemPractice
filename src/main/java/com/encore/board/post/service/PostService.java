package com.encore.board.post.service;

import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public Post postCreate(PostSaveReqDto postSaveReqDto) {
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(authorRepository.findByEmail(postSaveReqDto.getEmail()).orElse(null))
                .build();
        postRepository.save(post);
        return post;
    }

    public Post findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public List<PostListResDto> postList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedTimeDesc();
        List<PostListResDto> postListResDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
            postListResDto.setAuthor_email(post.getAuthor()==null?"익명":post.getAuthor().getEmail());
            postListResDtoList.add(postListResDto);
        }
        return postListResDtoList;
    }

    public PostDetailResDto PostDetail(Long id) {
        Post post = this.findById(id);
        PostDetailResDto postDetailResDto = PostDetailResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdTime(post.getCreatedTime())
                .build();
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
