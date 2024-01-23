package com.encore.board.post.controller;

import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/create")
    public String postGet(PostSaveReqDto postSaveReqDto) {
        return "/post/post-create";
    }

    @PostMapping("/post/create")
    public String postCreate(PostSaveReqDto postSaveReqDto) {
        Post post = postService.postCreate(postSaveReqDto);
        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String postList(Model model) {
        List<PostListResDto> postListResDtos = postService.postList();
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
    }

    @GetMapping("/post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        PostDetailResDto postDetailResDto = postService.PostDetail(id);
        model.addAttribute("post", postDetailResDto);
        return "post/post-detail";
    }

    @PostMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, PostUpdateReqDto postUpdateReqDto) {
        postService.postUpdate(id, postUpdateReqDto);
        return "redirect:/post/detail/" + id;
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.postDelete(id);
        return "redirect:/post/list";
    }
}
