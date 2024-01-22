package com.encore.board.author.controller;

import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;


    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.Save(authorSaveReqDto);
        return "Ok";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
        List<AuthorListResDto> authorListResDtos = authorService.findAll();
        model.addAttribute("authorList", authorListResDtos);
        return "/author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
        AuthorDetailResDto authorDetailResDto = authorService.findById(id);
        model.addAttribute("author", authorDetailResDto);
        return "/author/author-detail";
    }


}
