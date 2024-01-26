package com.encore.board.post.controller;

import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String postCreate(Model model, PostSaveReqDto postSaveReqDto) {
        try {
            postService.postCreate(postSaveReqDto);
            return "redirect:/post/list";
        } catch (IllegalArgumentException e){
            model.addAttribute(("errorMessage"),e.getMessage());
            return "post/post-create";
        }
    }

    @GetMapping("/post/list")
//    localhost:8080/post/list?size=xx&page=xx&sort=xx.desc
    public String postList(Model model,@PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListResDto> postListResDtos = postService.findByAppointment(pageable);
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
    }

//    @GetMapping("/json/post/list")
////    localhost:8080/post/list?size=xx&page=xx&sort=xx.desc
//    @ResponseBody
//    public Page<PostListResDto> postPage(Pageable pageable) {
//        Page<PostListResDto> postListResDtos = postService.findAllJson(pageable);
//        return postListResDtos;
//    }

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
